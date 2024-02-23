package com.prashant.greenusys.view

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.greenusys.adapter.CategoryAdapter
import com.prashant.greenusys.api.MealApiService
import com.prashant.greenusys.api.RetrofitClient
import com.prashant.greenusys.databinding.ActivityMainBinding
import com.prashant.greenusys.repository.MealRepository
import com.prashant.greenusys.viewmodel.MealViewModel
import com.prashant.greenusys.viewmodel.MealViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryAdapter = CategoryAdapter(this, mutableListOf())

        binding.mealsRecyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = categoryAdapter
        }

        val mealRepository = MealRepository(RetrofitClient.mealApiService())
        val viewModelFactory = MealViewModelFactory(mealRepository)

        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        mealViewModel.categories.observe(this, Observer { meals ->
            binding.pd.visibility =View.GONE
            binding.mainProgress.visibility =View.GONE
            binding.searchBtn.visibility =View.VISIBLE
            if (meals!=null){
                categoryAdapter.updateList(meals)

            }else{
                Toast.makeText(this, "Not available !" , Toast.LENGTH_LONG).show()
            }
        })


        val countryAdapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, countryNames)
        binding.searchBar.setAdapter(countryAdapter)



        binding.searchBtn.setOnClickListener {
            val category = binding.searchBar.text.toString().trim()
            if (category.isNotEmpty()) {
                binding.pd.visibility =View.VISIBLE
                binding.searchBtn.visibility =View.GONE
                mealViewModel.getCategoriesByCountry(category)
            }
        }

        mealViewModel.getCategoriesByCountry("Indian")
    }

    private val countryNames = listOf(
        "Indian", "Canadian", "Albanian", "Australian", "Brazilian",
        "Chilean", "Colombian", "Ecuadorian", "Egyptian", "Estonian",
        "Venezuelan", "Vietnamese"
    )

}
