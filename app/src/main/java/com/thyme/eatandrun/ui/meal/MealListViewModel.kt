package com.thyme.eatandrun.ui.meal

import androidx.lifecycle.ViewModel
import com.thyme.eatandrun.data.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel
@Inject
constructor(private val taskRepository: MealRepository) : ViewModel() {


    fun deleteAll() {
        GlobalScope.launch(Dispatchers.IO) {
            taskRepository.deleteAllMeals()
        }
    }


}
