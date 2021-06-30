package com.thyme.eatandrun.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDatabaseDao {

    @Insert
    suspend fun insert(food: MealModel)

    @Query("SELECT * FROM meal_table ORDER BY id DESC")
    fun getAllMeals(): Flow<List<MealModel>>

    @Query("SELECT * FROM meal_table WHERE date LIKE :day ORDER BY id ASC")
    fun getAllMealsFromDay(day: String): LiveData<List<MealModel>>

    @Query("DELETE FROM meal_table")
    fun deleteAll()

    @Delete
    fun deleteMeal(food: MealModel)

}