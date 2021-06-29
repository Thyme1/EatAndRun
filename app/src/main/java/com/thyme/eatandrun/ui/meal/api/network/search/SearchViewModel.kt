package com.thyme.eatandrun.ui.meal.api.network.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thyme.eatandrun.ui.meal.api.network.MealDatabaseApi
import com.thyme.eatandrun.ui.meal.api.network.model.Meal
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    // Word filter for API search
    val word = MutableLiveData<String>()

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    private val _searchListMeal = MutableLiveData<List<Meal>>()

    val searchListMeal: LiveData<List<Meal>>
        get() = _searchListMeal

    // Internally, we use a MutableLiveData to handle navigation to the selected food
    private val _navigateToSelectedMeal = MutableLiveData<Meal?>()

    val navigateToSelectedMeal: MutableLiveData<Meal?>
        get() = _navigateToSelectedMeal

    val searchInProgress = MutableLiveData<Boolean>()

    val showMealNotFound = MutableLiveData<Boolean>()

    init {
        searchInProgress.value = false
        showMealNotFound.value = false
    }


    fun getSearchMealResponse() {
        if (word.value == "" || word.value == null)
            return

        searchInProgress.value = true
        viewModelScope.launch {
            val responseDeffered = MealDatabaseApi.retrofitService.getSpecificMeal(word.value ?: "")
            try {
                val responseJson = responseDeffered.await()
                val hintsList = responseJson.hints
                searchInProgress.value = false
                showMealNotFound.value = false
                val auxMealList: MutableList<Meal> = mutableListOf()
                for (hint in hintsList) {
                    auxMealList.add(hint.meal)
                }
                _searchListMeal.value = auxMealList

            } catch (e: Exception) {
                searchInProgress.value = false
                showMealNotFound.value = true
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedMeal] [MutableLiveData]
     * @param food that was clicked
     */
    fun displayAddMeal(food: Meal) {
        _navigateToSelectedMeal.value = food
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedFood is set to null
     */
    fun displayAddMealIsComplete() {
        _navigateToSelectedMeal.value = null
    }

}