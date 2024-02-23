package com.prashant.greenusys.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    fun mealApiService(): MealApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MealApiService::class.java)
    }
}
