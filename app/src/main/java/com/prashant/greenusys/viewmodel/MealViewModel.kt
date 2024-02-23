package com.prashant.greenusys.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.greenusys.model.CategoryResponse
import com.prashant.greenusys.model.MealDetails
import com.prashant.greenusys.model.MealDetailsResponse
import com.prashant.greenusys.repository.MealRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    private val _categories = MutableLiveData<List<MealDetails>>()
    val categories: LiveData<List<MealDetails>> get() = _categories


    private val _selectedMealDetails = MutableLiveData<MealDetails>()
    val selectedMealDetails: LiveData<MealDetails> get() = _selectedMealDetails

    fun getCategoriesByCountry(country: String) {
        viewModelScope.launch {
            val response = repository.getCategoriesByCountry(country)
            handleCategoryResponse(response)
        }
    }

    fun getMealDetailsById(mealId: String) {
        viewModelScope.launch {
            val response = repository.getMealDetailsById(mealId)
            handleMealDetailsResponse(response)
        }
    }

    private fun handleCategoryResponse(response: Response<CategoryResponse>) {
        if (response.isSuccessful) {
            _categories.value = response.body()?.meals
        } else {
            Log.e("MealViewModel", "Error in response", )
            _categories.value = emptyList()
        }
    }
    private fun handleMealDetailsResponse(response: Response<MealDetailsResponse>) {
        if (response.isSuccessful) {
            _selectedMealDetails.value = response.body()?.meals?.get(0)
        } else {
            Log.e("MealViewModel", "Error in response", )
        }
    }
}
