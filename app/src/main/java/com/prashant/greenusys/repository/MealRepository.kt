package com.prashant.greenusys.repository

import com.prashant.greenusys.api.MealApiService
import com.prashant.greenusys.model.CategoryResponse
import com.prashant.greenusys.model.MealDetailsResponse
import retrofit2.Response

class MealRepository(private val apiService: MealApiService) {

    suspend fun getCategoriesByCountry(country: String): Response<CategoryResponse> {
        return apiService.getCategoriesByCountry(country)
    }

    suspend fun getMealDetailsById(mealId: String): Response<MealDetailsResponse> {
        return apiService.getMealDetailsById(mealId)
    }
}
