package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.model.PokemonGoStats
import com.example.pokegostats.room.entity.PokemonEntity
import kotlinx.android.synthetic.main.pokemon_stats_row_menu.view.*

class PokemonAdapter(context: Context) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var pokemon = emptyList<PokemonEntity>() // Cached copy of pokemon

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemon.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_stats_row_menu, parent, false)
        return PokemonViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val current = pokemon[position].pokemonName
        holder.tvPokemonType.text = current
    }

    internal fun setPokemon(pokemon: List<PokemonEntity>) {
        this.pokemon = pokemon
        notifyDataSetChanged()
    }

    // Links to TextView that is added to each row in the RecyclerView
    inner class PokemonViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvPokemonType = view.tv_menu_row_item
    }
}




