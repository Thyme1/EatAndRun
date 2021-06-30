package com.thyme.eatandrun.ui.meal.addMeal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.thyme.eatandrun.data.MealDatabaseDao
import com.thyme.eatandrun.data.MealModel
import com.thyme.eatandrun.ui.meal.api.network.model.Meal
import com.thyme.eatandrun.utils.getCurrentDayString
import com.thyme.todolist.R
import kotlinx.coroutines.*

class AddMealViewModel(
    meal: Meal,
    val database: MealDatabaseDao,
    app: Application
) : AndroidViewModel(app) {

    /** COROUTINES */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)


    /** BINDABLES */
    private val _selectedFood = MutableLiveData<Meal>()

    val selectedFood: LiveData<Meal>
        get() = _selectedFood

    val currentGramsString = MutableLiveData<String>()

    // Navigation Back to Overview
    val navigateToOverview = MutableLiveData<Boolean>()


    init {
        navigateToOverview.value = false
        _selectedFood.value = meal
        currentGramsString.value = "100"
    }

    /** UI's LiveData */

    val displayKcalPer100G = Transformations.map(selectedFood) { food ->
        app.applicationContext.getString(R.string.display_kcal_per_100g, food.nutrients.kcal)
    }


    val displayCurrentCarbs = Transformations.map(currentGramsString) { gramsString ->
        val carbsPerOneGram = selectedFood.value!!.nutrients.carbs / 100

        if (gramsString.isEmpty()) {
            app.applicationContext.getString(R.string.format_grams, "0".toDouble())
        } else {
            app.applicationContext.getString(R.string.format_grams, gramsString.toDouble().times(carbsPerOneGram))
        }
    }

    val displayCurrentProteins = Transformations.map(currentGramsString) { gramsString ->
        val proteinsPerOneGram = selectedFood.value!!.nutrients.protein / 100

        if (gramsString.isEmpty()) {
            app.applicationContext.getString(R.string.format_grams, "0".toDouble())
        } else {
            app.applicationContext.getString(R.string.format_grams, gramsString.toDouble().times(proteinsPerOneGram))
        }
    }

    val displayCurrentFats = Transformations.map(currentGramsString) { gramsString ->
        val fatsPerOneGram = selectedFood.value!!.nutrients.fat / 100

        if (gramsString.isEmpty()) {
            app.applicationContext.getString(R.string.format_grams, "0".toDouble())
        } else {
            app.applicationContext.getString(R.string.format_grams, gramsString.toDouble().times(fatsPerOneGram))
        }
    }

    val displayCurrentTotalKcal = Transformations.map(currentGramsString) { gramsString ->
        val kcalPerOneGram = selectedFood.value!!.nutrients.kcal / 100

        if (gramsString.isEmpty()) {
            app.applicationContext.getString(R.string.format_total_kcal, "0".toDouble())
        } else {
            app.applicationContext.getString(R.string.format_total_kcal, gramsString.toDouble().times(kcalPerOneGram))
        }
    }

    val displayCarbsPercent = Transformations.map(selectedFood) { food ->
        app.applicationContext.getString(R.string.format_percent, food.nutrients.carbsPercent)
    }

    val displayProteinsPercent = Transformations.map(selectedFood) { food ->
        app.applicationContext.getString(R.string.format_percent, food.nutrients.proteinPercent)
    }

    val displayFatsPercent = Transformations.map(selectedFood) { food ->
        app.applicationContext.getString(R.string.format_percent, food.nutrients.fatPercent)
    }

    /** Database */

    private suspend fun insert(foodModel: MealModel) {
        withContext(Dispatchers.IO) {
            database.insert(foodModel)
        }
    }

    fun onAddFoodSave() {
        uiScope.launch {
            val carbsPerOneGram = selectedFood.value!!.nutrients.carbs / 100
            val proteinsPerOneGram = selectedFood.value!!.nutrients.protein / 100
            val fatsPerOneGram = selectedFood.value!!.nutrients.fat / 100
            val kcalPerOneGram = selectedFood.value!!.nutrients.kcal / 100

            val currentGrams = currentGramsString.value!!.toDouble()

            val mealModel = MealModel(
                name = selectedFood.value?.layoutName,
                grams = currentGrams,
                carbs = currentGrams.times(carbsPerOneGram),
                proteins = currentGrams.times(proteinsPerOneGram),
                fats = currentGrams.times(fatsPerOneGram),
                kcal = currentGrams.times(kcalPerOneGram),
                date = getCurrentDayString()

            )

            insert(mealModel)
        }

        onNavigateToOverviewStart()
    }

    override fun onCleared() {
        super.onCleared()
        // cancel all coroutines
        viewModelJob.cancel()
    }

    /** Navigations*/
    fun onNavigateToOverviewStart() {
        navigateToOverview.value = true
    }

    fun onNavigateToOverviewCompleted() {
        navigateToOverview.value = false
    }
}