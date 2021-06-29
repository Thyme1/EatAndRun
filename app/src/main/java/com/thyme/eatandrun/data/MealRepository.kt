package com.thyme.eatandrun.data

import javax.inject.Inject

class MealRepository
@Inject
constructor(private val mealDao: MealDao) {

    suspend fun insertMeal(product: MealModel) = mealDao.insert(product)
    suspend fun deleteMeal(product: MealModel) = mealDao.deleteMeal(product)
    fun getAllMeals() = mealDao.getAllMeals()
    fun deleteAllMeals() = mealDao.deleteAll()

}