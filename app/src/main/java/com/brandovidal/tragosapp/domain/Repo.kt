package com.brandovidal.tragosapp.domain

import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.vo.Resource

interface Repo {
    suspend fun getDrinkList(drinkName: String): Resource<List<Drink>>
    suspend fun getDrinkFavorites(): Resource<List<DrinkEntity>>
    suspend fun insertDrink(drink: DrinkEntity)
    suspend fun deleteDrink(drink: DrinkEntity)
}