package com.thyme.eatandrun.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thyme.eatandrun.network.FoodDatabaseApi
import com.thyme.eatandrun.network.model.Food
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val word = MutableLiveData<String>()

    private val _response = MutableLiveData<String>()

    private val _searchListFood = MutableLiveData<List<Food>>()

    val searchListFood: LiveData<List<Food>>
        get() = _searchListFood

    private val _navigateToSelectedFood = MutableLiveData<Food?>()

    val navigateToSelectedFood: MutableLiveData<Food?>
        get() = _navigateToSelectedFood

    val searchInProgress = MutableLiveData<Boolean>()

    val showFoodNotFound = MutableLiveData<Boolean>()

    init {
        searchInProgress.value = false
        showFoodNotFound.value = false
    }


    fun getSearchFoodResponse() {
        if (word.value == "" || word.value == null)
            return

        searchInProgress.value = true
        viewModelScope.launch {
            val responseDeffered = FoodDatabaseApi.retrofitService.getSpecificFood(word.value ?: "")
            try {
                val responseJson = responseDeffered.await()
                val hintsList = responseJson.hints
                searchInProgress.value = false
                showFoodNotFound.value = false
                val auxFoodList: MutableList<Food> = mutableListOf()
                for (hint in hintsList) {
                    auxFoodList.add(hint.food)
                }
                _searchListFood.value = auxFoodList

            } catch (e: Exception) {
                searchInProgress.value = false
                showFoodNotFound.value = true
                _response.value = "Failure: ${e.message}"
            }
        }
    }


    fun displayAddFood(food: Food) {
        _navigateToSelectedFood.value = food
    }


    fun displayAddFoodIsComplete() {
        _navigateToSelectedFood.value = null
    }

}