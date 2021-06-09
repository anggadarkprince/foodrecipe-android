package com.anggaari.foodrecipe.data

import com.anggaari.foodrecipe.data.network.FoodRecipesApi
import com.anggaari.foodrecipe.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipesApi: FoodRecipesApi) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}