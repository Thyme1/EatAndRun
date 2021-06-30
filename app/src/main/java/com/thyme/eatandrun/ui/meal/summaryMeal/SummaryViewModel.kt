package com.thyme.eatandrun.ui.meal.summaryMeal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SummaryViewModel: ViewModel() {
    var _calories = MutableLiveData<Double>()

    var _proteins = MutableLiveData<Double>()
    val proteins : LiveData<Double> = _proteins

    var _fats = MutableLiveData<Double>()
    val fats : LiveData<Double> = _fats

    var _carbs = MutableLiveData<Double>()
    val carbs : LiveData<Double> = _carbs

}