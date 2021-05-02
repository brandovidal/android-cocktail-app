package com.brandovidal.tragosapp.domain

import androidx.room.*
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity

@Dao
interface DrinkDao {
    @Query("SELECT * FROM drinkEntity")
    suspend fun getAllFavoriteDrinks(): List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(drink: DrinkEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)
}