package com.anggaari.foodrecipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anggaari.foodrecipe.models.FoodRecipe
import com.anggaari.foodrecipe.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}