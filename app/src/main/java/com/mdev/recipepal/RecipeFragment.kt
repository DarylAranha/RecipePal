package com.mdev.recipepal

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class RecipeFragment : Fragment() {

        companion object {
            fun newInstance(recipe: Recipe): RecipeFragment {
                val args = Bundle().apply {
                    putParcelable("recipe", recipe)
                }
                val fragment = RecipeFragment()
                fragment.arguments = args
                return fragment
            }
        }

        private lateinit var recipe: Recipe

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                recipe = it.getParcelable("recipe")!!
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)
        if (::recipe.isInitialized) {
            view.findViewById<TextView>(R.id.recipeTitle)?.text = recipe.title
            view.findViewById<TextView>(R.id.recipeDesc)?.text = "Ingredients: \n"+recipe.ingredients.toString()+"\n"+"Directions: \n"+recipe.instructions
            //view.findViewById<ImageView>(R.id.recipeImage)?.setImageDrawable(recipe.imageUrl)
            Glide.with(this)
                .load(recipe.imageUrl)
                .into( view.findViewById<ImageView>(R.id.recipeImage))
            // ...
        }
        return view
    }
    }

