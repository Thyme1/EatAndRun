package com.thyme.eatandrun.ui.meal.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.thyme.eatandrun.data.MealDatabaseDao
import com.thyme.eatandrun.data.MealModel
import com.thyme.eatandrun.utils.getCurrentDayString
import com.thyme.todolist.R
import kotlinx.coroutines.*

class OverviewViewModel(
    val database: MealDatabaseDao,
    app: Application) : AndroidViewModel(app) {

    /** COROUTINES */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)


    var dateSelected = MutableLiveData(getCurrentDayString())

    /** LIVEDATA */
//    val foods = database.getAllFoodFromDay(dateSelected.value ?: "")

    val meals = Transformations.switchMap(dateSelected) { date ->
        database.getAllMealsFromDay(date)
    }


    val mealTotal = Transformations.map(meals) { meals ->
        var gramsTotal = 0.0
        var carbsTotal = 0.0
        var proteinsTotal = 0.0
        var fatsTotal = 0.0
        var kcalTotal = 0.0

        for (meal in meals) {
            gramsTotal += meal.grams
            carbsTotal += meal.carbs
            proteinsTotal += meal.proteins
            fatsTotal += meal.fats
            kcalTotal += meal.kcal
        }

        MealModel(
            name = "",
            grams = gramsTotal,
            carbs = carbsTotal,
            proteins = proteinsTotal,
            fats = fatsTotal,
            kcal = kcalTotal,
            date = ""
        )

    }

    val displayTotalKcal = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_double, meal.kcal)
    }

    val displayTotalCarbs = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_double, meal.carbs)
    }

    val displayTotalProteins = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_double, meal.proteins)
    }

    val displayTotalFats = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_double, meal.fats)
    }

    val displayCarbsPercent = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_percent, meal.carbsPercent)
    }

    val displayProteinsPercent = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_percent, meal.proteinPercent)
    }

    val displayFatsPercent = Transformations.map(mealTotal) { meal ->
        app.applicationContext.getString(R.string.format_percent, meal.fatPercent)
    }

    /** DATABASE */
    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.deleteAll()
        }
    }

    private suspend fun deleteMeal(mealModel: MealModel) {
        withContext(Dispatchers.IO) {
            database.deleteMeal(mealModel)
        }
    }

    fun onDeleteChoosedMeal(mealModel: MealModel) {
        uiScope.launch {
            deleteMeal(mealModel)
        }
    }



    override fun onCleared() {
        super.onCleared()
        // cancel all coroutines
        viewModelJob.cancel()
    }
}