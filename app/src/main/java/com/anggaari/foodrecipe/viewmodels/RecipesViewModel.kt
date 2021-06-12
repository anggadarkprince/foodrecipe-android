package com.anggaari.foodrecipe.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anggaari.foodrecipe.utils.Constants.Companion.API_KEY
import com.anggaari.foodrecipe.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.anggaari.foodrecipe.utils.Constants.Companion.QUERY_API_KEY
import com.anggaari.foodrecipe.utils.Constants.Companion.QUERY_DIET
import com.anggaari.foodrecipe.utils.Constants.Companion.QUERY_FILL_INGREDIENT
import com.anggaari.foodrecipe.utils.Constants.Companion.QUERY_NUMBER
import com.anggaari.foodrecipe.utils.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application): AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENT] = "true"

        return queries
    }
}