package com.prashant.greenusys.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prashant.greenusys.R
import com.prashant.greenusys.model.MealDetails
import com.prashant.greenusys.view.MealDetailActivity
import com.squareup.picasso.Picasso

class CategoryAdapter(
    private val context: Context, private var meals: List<MealDetails>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cl_meal_item_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, MealDetailActivity::class.java)
            intent.putExtra("mealId", meal.idMeal)
            intent.putExtra("mealName", meal.strMeal)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun updateList(newList: List<MealDetails>) {
        meals = newList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealTitleTextView)
        private val mealDescriptionTextView: TextView =
            itemView.findViewById(R.id.mealDetailTextView)
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)

        fun bind(meal: MealDetails) {

            val randomDescription = generateRandomDescription(meal.strMeal)

            mealNameTextView.text = meal.strMeal
            mealDescriptionTextView.text = randomDescription
            Picasso.get().load(meal.strMealThumb).into(mealImageView)

        }

        private fun generateRandomDescription(dishName: String): String {
            val randomAdjectives =
                listOf("Delicious", "Savory", "Exquisite", "Mouth-watering", "Scrumptious")
            val randomVerbs = listOf("crafted", "prepared", "cooked", "served", "seasoned")
            val randomNouns = listOf("dish", "cuisine", "delight", "creation", "feast")

            val randomAdjective = randomAdjectives.random()
            val randomVerb = randomVerbs.random()
            val randomNoun = randomNouns.random()

            return "$dishName: $randomAdjective $randomVerb $randomNoun. A true culinary delight in every bite!"
        }
    }


}
