package com.thyme.eatandrun.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Meal::class],
    version = 1, exportSchema = false
)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
}