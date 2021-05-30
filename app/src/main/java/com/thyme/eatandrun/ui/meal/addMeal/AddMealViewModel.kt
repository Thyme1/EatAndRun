package com.thyme.eatandrun.ui.meal.addMeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thyme.eatandrun.data.Meal
import com.thyme.eatandrun.data.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMealViewModel
@Inject
constructor(private val mealRepository: MealRepository) : ViewModel() {

    fun insertMeal(meal: Meal) = viewModelScope.launch {
        mealRepository.insertMeal(meal)
    }

    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        mealRepository.deleteMeal(meal)
    }

    val allToDos = mealRepository.getAllMeals().asLiveData() //as Live data


}