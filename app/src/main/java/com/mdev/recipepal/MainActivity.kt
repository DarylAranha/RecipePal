package com.mdev.recipepal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: RecyclerView
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recipeList = findViewById(R.id.recipeList)
        tabLayout = findViewById(R.id.tabLayout)
        val searchView = findViewById<SearchView>(R.id.searchView)
        recipeList.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(getRecipes())
        recipeList.adapter = recipeAdapter

//        tabLayout.addTab(tabLayout.newTab().setText("Home"))/testing
//        tabLayout.addTab(tabLayout.newTab().setText("Grocery List"))
//        tabLayout.addTab(tabLayout.newTab().setText("User Info"))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recipeAdapter.filter.filter(newText)
                return true
            }
        })
    }


    private fun getRecipes(): List<Recipe> {
        // Dummy data for the recipe list
        val recipes = mutableListOf<Recipe>()
        recipes.add(Recipe("Chicken Alfredo"))
        recipes.add(Recipe("Beef Stir Fry"))
        recipes.add(Recipe("Vegetable Curry"))
        recipes.add(Recipe("Pesto Pasta"))
        recipes.add(Recipe("Lentil Soup"))
        recipes.add(Recipe("Salmon with Asparagus"))
        return recipes
    }
}