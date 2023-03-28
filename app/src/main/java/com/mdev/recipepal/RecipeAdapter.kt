package com.mdev.recipepal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class RecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(), Filterable {

    private var filteredRecipes: List<Recipe> = recipes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_list_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = filteredRecipes[position]
        holder.recipeTitle.text = recipe.title
    }

    override fun getItemCount(): Int {
        return filteredRecipes.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().toLowerCase(Locale.ROOT)
                val filteredList = ArrayList<Recipe>()
                if (query.isEmpty()) {
                    filteredList.addAll(recipes)
                } else {
                    for (recipe in recipes) {
                        if (recipe.title.toLowerCase(Locale.ROOT).contains(query)) {
                            filteredList.add(recipe)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredRecipes = results?.values as List<Recipe>
                notifyDataSetChanged()
            }
        }
    }
}
