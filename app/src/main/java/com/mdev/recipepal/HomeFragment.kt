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
import com.google.android.material.bottomnavigation.BottomNavigationView


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

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Hide the BottomNavigationView
        bottomNavigationView.visibility = View.VISIBLE

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    findNavController().navigate(R.id.homeFragment)
                    true
                }
                R.id.userInformation -> {
                    findNavController().navigate(R.id.userInformationFragment)
                    true
                }
                else -> false
            }
        }


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
                "Avocado Toast",
                listOf("1 slice bread", "1/2 avocado, mashed", "Salt and pepper, to taste"),
                "1. Toast the bread to your desired level of crispiness.\n2. Spread the mashed avocado onto the toast.\n3. Sprinkle with salt and pepper, to taste.",
                "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimg1.cookinglight.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2F2018%2F07%2Fmain%2F1807w-avocado-toast-recipe.jpg%3Fitok%3D_dDi7ZQQ"
            ),
            Recipe(
                "Greek Yogurt Parfait",
                listOf("1/2 cup Greek yogurt", "1/4 cup granola", "1/2 cup mixed berries"),
                "1. In a small bowl or jar, layer the Greek yogurt, granola, and mixed berries.\n2. Repeat the layering until all ingredients are used up.\n3. Serve immediately.",
                "https://simplyhomecooked.com/wp-content/uploads/2021/08/yogurt-parfait-4.jpg"
            ),
            Recipe(
                "One-Pot Pasta",
                listOf("8 oz spaghetti", "1 can diced tomatoes", "2 cups water", "2 cloves garlic, minced", "1 tsp dried basil", "Salt and pepper, to taste"),
                "1. In a large pot, combine the spaghetti, diced tomatoes, water, minced garlic, and dried basil.\n2. Bring to a boil, then reduce the heat to low and let simmer for 10-12 minutes, or until the pasta is cooked through.\n3. Season with salt and pepper, to taste.\n4. Serve hot, garnished with grated Parmesan cheese, if desired.",
                "https://www.jocooks.com/wp-content/uploads/2022/09/one-pot-pasta-1-11.jpg"
            ),
            Recipe(
                "Banana Smoothie",
                listOf("1 ripe banana", "1/2 cup milk", "1/2 cup plain Greek yogurt", "1 tsp honey"),
                "1. In a blender, combine the banana, milk, Greek yogurt, and honey.\n2. Blend until smooth and creamy.\n3. Serve immediately.",
                "https://www.thespruceeats.com/thmb/67fUhzexgkuAZZkNPCzN_L54Vn8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/banana-smoothie-recipes-759606-hero-01-d2abaa79f3204030a0ec0a8940456acc.jpg"
            ),
            Recipe(
                "Baked Chicken",
                listOf("4 boneless, skinless chicken breasts", "2 tbsp olive oil", "1 tsp paprika", "1 tsp garlic powder", "Salt and pepper, to taste"),
                "1. Preheat the oven to 400°F.\n2. Place the chicken breasts in a baking dish and brush with olive oil.\n3. Sprinkle the paprika, garlic powder, salt, and pepper over the chicken.\n4. Bake for 20-25 minutes, or until the chicken is cooked through and no longer pink in the middle.\n5. Serve hot, garnished with fresh herbs or lemon wedges, if desired.",
                "https://therecipecritic.com/wp-content/uploads/2021/07/bakedchicken-1-500x375.jpg"
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
