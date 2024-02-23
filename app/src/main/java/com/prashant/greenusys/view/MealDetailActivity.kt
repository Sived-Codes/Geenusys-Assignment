package com.prashant.greenusys.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.prashant.greenusys.api.RetrofitClient
import com.prashant.greenusys.databinding.ActivityMealDetailBinding
import com.prashant.greenusys.repository.MealRepository
import com.prashant.greenusys.viewmodel.MealViewModel
import com.prashant.greenusys.viewmodel.MealViewModelFactory
import com.squareup.picasso.Picasso

class MealDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailBinding
    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealId = intent.getStringExtra("mealId")
        val mealName = intent.getStringExtra("mealName")

        binding.backBtn.setOnClickListener {
            finish()
        }

        if (mealId != null && mealName != null) {
            getMealDetail(mealId)
        } else {
            finish()
        }


    }

    private fun getMealDetail(mealId: String) {
        val mealRepository = MealRepository(RetrofitClient.mealApiService())
        val viewModelFactory = MealViewModelFactory(mealRepository)

        mealViewModel = ViewModelProvider(this, viewModelFactory).get(MealViewModel::class.java)

        mealId.let {
            mealViewModel.getMealDetailsById(it)
        }

        mealViewModel.selectedMealDetails.observe(this) { mealDetails ->

            binding.pd.visibility =View.GONE
            binding.mainlayout.visibility =View.VISIBLE

            Picasso.get().load(mealDetails.strMealThumb).into(binding.dishImageView)
            binding.dishNameTextView.text =mealDetails.strMeal
            binding.tagsTextView.text =mealDetails.strTags
            binding.categoryAreaTextView.text =mealDetails.strCategory+ ", ${mealDetails.strArea}"
            binding.instructionsTextView.text =mealDetails.strInstructions
            binding.sourceLinkTextView.text =mealDetails.strSource
            binding.youtubeLinkTextView.text =mealDetails.strYoutube
        }
    }
}
