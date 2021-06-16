package com.anggaari.foodrecipe.ui.fragments.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.anggaari.foodrecipe.databinding.FragmentInstructionBinding
import com.anggaari.foodrecipe.models.Result
import com.anggaari.foodrecipe.utils.Constants

class InstructionFragment : Fragment() {
    private var _binding: FragmentInstructionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding.instructionWebView.webViewClient = object : WebViewClient() {}
        binding.instructionWebView.loadUrl(myBundle!!.sourceUrl)

        return binding.root
    }
}