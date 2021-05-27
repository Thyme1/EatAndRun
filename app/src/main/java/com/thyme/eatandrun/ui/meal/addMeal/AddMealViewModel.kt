package com.thyme.eatandrun.ui.meal.addMeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thyme.eatandrun.data.Meal
import com.thyme.eatandrun.data.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMealViewModel
@Inject
constructor(private val taskRepository: MealRepository) : ViewModel() {

    fun insertTask(task: Meal) = viewModelScope.launch {
        taskRepository.insertMeal(task)
    }

    fun deleteTask(task: Meal) = viewModelScope.launch {
        taskRepository.deleteMeal(task)
    }

    val allToDos = taskRepository.getAllTasks().asLiveData()


}