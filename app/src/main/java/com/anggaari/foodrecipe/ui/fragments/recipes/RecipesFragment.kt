package com.anggaari.foodrecipe.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggaari.foodrecipe.R
import com.anggaari.foodrecipe.viewmodels.MainViewModel
import com.anggaari.foodrecipe.adapters.RecipesAdapter
import com.anggaari.foodrecipe.databinding.FragmentRecipesBinding
import com.anggaari.foodrecipe.utils.MarginItemDecoration
import com.anggaari.foodrecipe.utils.NetworkListener
import com.anggaari.foodrecipe.utils.NetworkResult
import com.anggaari.foodrecipe.utils.observeOnce
import com.anggaari.foodrecipe.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipesViewModel
    private val adapter by lazy { RecipesAdapter() }

    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
        recipeViewModel.readBackOnline.observe(viewLifecycleOwner, {
            recipeViewModel.backOnline = it
        })

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipeViewModel.networkStatus = status
                    recipeViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.recipeFloatingActionButton.setOnClickListener {
            if (recipeViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipeBottomSheet)
            } else {
                recipeViewModel.showNetworkStatus()
            }
        }

        return binding.root;
    }

    private fun setupRecyclerView() {
        binding.recipeRecyclerView.adapter = adapter
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.defaultSpace
                )
            )
        )
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase() called")
                    Log.d("RecipesFragment", database[0].foodRecipe.toString())
                    adapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData() called")
        mainViewModel.getRecipes(recipeViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    private fun showShimmerEffect() {
        binding.recipeRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recipeRecyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}