package com.anggaari.foodrecipe.ui.fragments.ingredient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggaari.foodrecipe.R
import com.anggaari.foodrecipe.adapters.IngredientsAdapter
import com.anggaari.foodrecipe.databinding.FragmentIngredientBinding
import com.anggaari.foodrecipe.models.Result
import com.anggaari.foodrecipe.utils.Constants.Companion.RECIPE_RESULT_KEY
import com.anggaari.foodrecipe.utils.MarginItemDecoration

class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView()
        myBundle?.extendedIngredients?.let { adapter.setData(it) }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.ingredientRecyclerView.adapter = adapter
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ingredientRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.defaultSpace
                )
            )
        )
    }
}