package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.model.PokemonGoStats
import kotlinx.android.synthetic.main.pokemon_stats_row_menu.view.*

class PokemonAdapter(val items : ArrayList<PokemonGoStats>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.pokemon_stats_row_menu,
                parent,
                false
            )
        )
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPokemonType?.text = items[position].PokemonName
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvPokemonType = view.tv_menu_row_item
}