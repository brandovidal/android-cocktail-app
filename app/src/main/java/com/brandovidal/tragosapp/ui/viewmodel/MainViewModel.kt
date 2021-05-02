package com.brandovidal.tragosapp.ui.viewmodel

import androidx.lifecycle.*
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.domain.Repo
import com.brandovidal.tragosapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repo: Repo) : ViewModel() {

    private val drinksData = MutableLiveData<String>()

    fun setDrink(drinkName: String) {
        drinksData.value = drinkName
    }

    init {
        setDrink("margarita")
    }

    val fetchDrinkList = drinksData.distinctUntilChanged().switchMap { drinkName ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(repo.getDrinkList(drinkName))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveDrink(drink: DrinkEntity) {
        viewModelScope.launch {
            repo.insertDrink(drink)
        }
    }

    fun getDrinkFavorites() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repo.getDrinkFavorites())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun deleteDrink(drink: DrinkEntity) {
        viewModelScope.launch {
            repo.deleteDrink(drink)
        }
    }
}