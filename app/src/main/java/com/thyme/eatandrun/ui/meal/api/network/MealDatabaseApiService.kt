package com.thyme.eatandrun.ui.meal.api.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.thyme.eatandrun.ui.meal.api.network.model.ResponseJson
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.edamam.com/api/food-database/"
private const val APP_ID = "3864e61a"
private const val APP_KEY = "03011149ae12a4b04b5049d50bee36fa"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MealDatabaseApiService {

    @GET("parser?")
    fun getSpecificMeal(@Query("ingr") food: String,
                        @Query("app_id") appId: String = APP_ID,
                        @Query("app_key") appKey: String = APP_KEY):
            Deferred<ResponseJson>
}

object MealDatabaseApi {
    val retrofitService: MealDatabaseApiService by lazy { retrofit.create(MealDatabaseApiService::class.java) }
}