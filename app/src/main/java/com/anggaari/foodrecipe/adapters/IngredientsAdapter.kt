package com.anggaari.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anggaari.foodrecipe.R
import com.anggaari.foodrecipe.databinding.IngredientRowLayoutBinding
import com.anggaari.foodrecipe.models.ExtendedIngredient
import com.anggaari.foodrecipe.utils.Constants.Companion.BASE_IMAGE_URL
import com.anggaari.foodrecipe.utils.RecipesDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient) {
            binding.ingredientImageView.load(BASE_IMAGE_URL + ingredient.image) {
                crossfade(600)
                placeholder(R.drawable.ic_error_placeholder)
                error(R.drawable.ic_error_placeholder)
            }
            binding.ingredientName.text = ingredient.name.replaceFirstChar { it.uppercase() }
            binding.ingredientAmount.text = ingredient.amount.toString()
            binding.ingredientUnit.text = ingredient.unit
            binding.ingredientConsistency.text = ingredient.consistency.replaceFirstChar { it.uppercase() }
            binding.ingredientOriginal.text = ingredient.original
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}