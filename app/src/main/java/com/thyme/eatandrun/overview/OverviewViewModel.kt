package com.thyme.eatandrun.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.thyme.eatandrun.database.FoodDatabaseDao
import com.thyme.eatandrun.database.FoodModel
import com.thyme.eatandrun.getCurrentDayString
import com.thyme.todolist.R
import kotlinx.coroutines.*

class OverviewViewModel(
    val database: FoodDatabaseDao,
    app: Application) : AndroidViewModel(app) {

    /** COROUTINES */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)


    var dateSelected = MutableLiveData(getCurrentDayString())
    fun setDateSelected(newDate: String) {
        dateSelected.value = newDate
    }
    /** LIVEDATA */
    val foods = Transformations.switchMap(dateSelected) { date ->
        database.getAllFoodFromDay(date)
    }


    val foodTotal = Transformations.map(foods) {foods ->
        var gramsTotal = 0.0
        var carbsTotal = 0.0
        var proteinsTotal = 0.0
        var fatsTotal = 0.0
        var kcalTotal = 0.0

        for (food in foods) {
            gramsTotal += food.grams
            carbsTotal += food.carbs
            proteinsTotal += food.proteins
            fatsTotal += food.fats
            kcalTotal += food.kcal
        }

        FoodModel(
            name = "",
            grams = gramsTotal,
            carbs = carbsTotal,
            proteins = proteinsTotal,
            fats = fatsTotal,
            kcal = kcalTotal,
            date = ""
        )

    }

    val displayTotalKcal = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_double, food.kcal)
    }

    val displayTotalCarbs = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_double, food.carbs)
    }

    val displayTotalProteins = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_double, food.proteins)
    }

    val displayTotalFats = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_double, food.fats)
    }

    val displayCarbsPercent = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_percent, food.carbsPercent)
    }

    val displayProteinsPercent = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_percent, food.proteinPercent)
    }

    val displayFatsPercent = Transformations.map(foodTotal) { food ->
        app.applicationContext.getString(R.string.format_percent, food.fatPercent)
    }

    /** DATABASE */
    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.deleteAll()
        }
    }

    fun onDeleteAll() {
        uiScope.launch {
            deleteAll()
        }
    }

    private suspend fun deleteFood(foodModel: FoodModel) {
        withContext(Dispatchers.IO) {
            database.deleteFood(foodModel)
        }
    }

    fun onDeleteChoosedFood(foodModel: FoodModel) {
        uiScope.launch {
            deleteFood(foodModel)
        }
    }



    override fun onCleared() {
        super.onCleared()
        // cancel all coroutines
        viewModelJob.cancel()
    }
}