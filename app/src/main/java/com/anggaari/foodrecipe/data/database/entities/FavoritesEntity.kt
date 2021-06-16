package com.anggaari.foodrecipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anggaari.foodrecipe.models.Result
import com.anggaari.foodrecipe.utils.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)