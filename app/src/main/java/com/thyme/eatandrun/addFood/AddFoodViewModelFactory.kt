package com.thyme.eatandrun.addFood

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thyme.eatandrun.database.FoodDatabaseDao


class AddFoodViewModelFactory(
    private val food:  com.thyme.eatandrun.network.model.Food,
    private val dataSource: FoodDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFoodViewModel::class.java)) {
            return AddFoodViewModel(food, dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}