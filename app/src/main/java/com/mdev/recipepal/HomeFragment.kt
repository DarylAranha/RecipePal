package com.mdev.recipepal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Set up the RecyclerView with the recipe list
        val recipeList = rootView.findViewById<RecyclerView>(R.id.recipeList)
        recipeList.layoutManager = LinearLayoutManager(activity)
        recipeAdapter = RecipeAdapter(getRecipes())
        recipeList.adapter = recipeAdapter

        return rootView
    }

    private fun getRecipes(): List<Recipe> {
        // Replace with your own data source
        return listOf(
            Recipe("Spaghetti Bolognese"),
            Recipe("Chicken Curry"),
            Recipe("Beef Stroganoff"),
            Recipe("Vegetable Stir Fry"),
            Recipe("Pizza Margherita"),
            Recipe("Fish Tacos")
        )
    }

}
