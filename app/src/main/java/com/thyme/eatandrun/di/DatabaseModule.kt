package com.thyme.eatandrun.di

import android.content.Context
import androidx.room.Room
import com.thyme.eatandrun.data.MealDatabase
import com.thyme.eatandrun.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMealDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, MealDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideMealDao(
        db: MealDatabase
    ) = db.mealDao()

}