package com.thyme.eatandrun.data

import javax.inject.Inject

class MealRepository
@Inject
constructor(private val mealDao: MealDao) {

    suspend fun insertMeal(product: Meal) = mealDao.insert(product)
    suspend fun deleteMeal(product: Meal) = mealDao.deleteMeal(product)
    fun getAllMeals() = mealDao.getAllMeals()
    fun deleteAllMeals() = mealDao.deleteAll()

}