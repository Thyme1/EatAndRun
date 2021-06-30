package com.thyme.eatandrun.ui.meal.addMeal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thyme.eatandrun.data.MealDatabaseDao
import com.thyme.eatandrun.ui.meal.api.network.model.Meal

class AddMealViewModelFactory(
    private val meal: Meal,
    private val dataSource: MealDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMealViewModel::class.java)) {
            return AddMealViewModel(meal, dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}