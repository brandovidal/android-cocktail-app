package com.brandovidal.tragosapp.data

import com.brandovidal.tragosapp.AppDatabase
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.vo.Resource
import com.brandovidal.tragosapp.vo.RetrofitClient

class DataSource(private val appDatabase: AppDatabase) {

    suspend fun getDrinkByName(drinkName: String): Resource<List<Drink>> {
//        return Resource.Success(RetrofitClient.webService.getDrinkByName(drinkName).drinkList)

        val resultRequest = RetrofitClient.webService.getDrinkByName(drinkName).drinkList
        resultRequest?.let { return Resource.Success(it) }
        var e = Exception("Result not found")
        return Resource.Failure(e)
    }

    suspend fun insertDrinkIntoRoom(drink: DrinkEntity) {
        appDatabase.drinkDao().insertFavorite(drink)
    }

    suspend fun getDrinkFavorites(): Resource<List<DrinkEntity>> {
        return Resource.Success(appDatabase.drinkDao().getAllFavoriteDrinks())
    }

    suspend fun deleteDrink(drink: DrinkEntity) {
        appDatabase.drinkDao().deleteDrink(drink)
    }
}