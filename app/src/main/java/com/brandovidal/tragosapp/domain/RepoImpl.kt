package com.brandovidal.tragosapp.domain

import com.brandovidal.tragosapp.data.DataSource
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.vo.Resource

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getDrinkList(drinkName: String): Resource<List<Drink>> {
        return dataSource.getDrinkByName(drinkName)
    }

    override suspend fun getDrinkFavorites(): Resource<List<DrinkEntity>> {
        return dataSource.getDrinkFavorites()
    }

    override suspend fun insertDrink(drink: DrinkEntity) {
        dataSource.insertDrinkIntoRoom(drink)
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        dataSource.deleteDrink(drink)
    }
}