package com.mdev.recipepal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment(), SearchView.OnQueryTextListener , RecipeAdapter.OnRecipeClickListener {

    private lateinit var recipeAdapter: RecipeAdapter
//    private lateinit var recipeList: RecyclerView
    private lateinit var searchBar: SearchView

    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val navController = findNavController()// Set up the RecyclerView with the recipe list
        val recipeList = rootView.findViewById<RecyclerView>(R.id.recipeList)
        recipeList.layoutManager = LinearLayoutManager(activity)
        recipeAdapter = RecipeAdapter(getRecipes())
        recipeList.adapter = recipeAdapter
        searchBar = rootView.findViewById<SearchView>(R.id.searchView)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recipeAdapter.filter.filter(newText)
                return false
            }

        })

        recipeAdapter.setOnRecipeClickListener(object : RecipeAdapter.OnRecipeClickListener {
            override fun onRecipeClick(recipe: Recipe) {
                val bundle = bundleOf("recipe" to recipe)
                navController?.navigate(R.id.action_homeFragment_to_recipeFragment, bundle);
            }
        })



        return rootView
    }

    private fun getRecipes(): List<Recipe> {
        // Replace with your own data source
        return listOf(
            Recipe(
                "Recipe 1",
                listOf("Ingredient 1", "Ingredient 2", "Ingredient 3"),
                "Instructions 1",
                "https://www.example.com/image1.jpg"
            ),
            Recipe(
                "Recipe 2",
                listOf("Ingredient 4", "Ingredient 5", "Ingredient 6"),
                "Instructions 2",
                "https://www.example.com/image2.jpg"
            ),
            Recipe(
                "Recipe 3",
                listOf("Ingredient 7", "Ingredient 8", "Ingredient 9"),
                "Instructions 3",
                "https://www.example.com/image3.jpg"
            ),
            Recipe(
                "Recipe 4",
                listOf("Ingredient 10", "Ingredient 11", "Ingredient 12"),
                "Instructions 4",
                "https://www.example.com/image4.jpg"
            ),
            Recipe(
                "Recipe 5",
                listOf("Ingredient 13", "Ingredient 14", "Ingredient 15"),
                "Instructions 5",
                "https://www.example.com/image5.jpg"
            )
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        recipeAdapter.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean{
        recipeAdapter.filter.filter(newText)
        return true
    }

    override fun onRecipeClick(recipe: Recipe) {

        navController?.navigate(R.id.action_homeFragment_to_recipeFragment);
    }

}
