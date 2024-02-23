package com.prashant.greenusys.api


import com.prashant.greenusys.model.CategoryResponse
import com.prashant.greenusys.model.MealDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("filter.php")
    suspend fun getCategoriesByCountry(@Query("a") country: String): Response<CategoryResponse>

    @GET("lookup.php")
    suspend fun getMealDetailsById(@Query("i") mealId: String): Response<MealDetailsResponse>
}
