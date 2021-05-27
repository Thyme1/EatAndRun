package com.thyme.eatandrun.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealDao {

    @Insert
    fun insert(food: Meal)

    @Query("SELECT * FROM meal_table ORDER BY id DESC")
    fun getAllMeals(): LiveData<List<Meal>>

    @Query("SELECT * FROM meal_table WHERE date LIKE :day ORDER BY id ASC")
    fun getAllMealsFromDay(day: String): LiveData<List<Meal>>

    @Query("DELETE FROM meal_table")
    fun deleteAll()

    @Delete
    fun deleteMeal(food: Meal)

}