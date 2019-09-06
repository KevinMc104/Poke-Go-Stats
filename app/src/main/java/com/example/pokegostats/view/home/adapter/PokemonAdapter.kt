package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonAndPokemonTypes
import kotlinx.android.synthetic.main.pokemon_stats_row_menu.view.*

class PokemonAdapter(context: Context) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var pokemonTypes = emptyList<PokemonAndPokemonTypes>() // Cached copy of pokemonTypes

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonTypes.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_stats_row_menu, parent, false)
        return PokemonViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val current = pokemonTypes[position].pokemon!!.pokemonName

        var currentType1 = ""
        var currentType2 = ""
        if(pokemonTypes[position].pokemonsTypes!!.size == 1) {
            currentType1 = pokemonTypes[position].pokemonsTypes!![0].type!!
        } else if(pokemonTypes[position].pokemonsTypes!!.size > 1) {
            currentType1 = pokemonTypes[position].pokemonsTypes!![0].type!!
            currentType2 = pokemonTypes[position].pokemonsTypes!![1].type!!
        }

        holder.tvPokemonName.text = current
        holder.tvPokemonType1.text = currentType1
        holder.tvPokemonType2.text = currentType2
    }

    internal fun setPokemonAndPokemonTypes(pokemonTypes: List<PokemonAndPokemonTypes>) {
        this.pokemonTypes = pokemonTypes
        notifyDataSetChanged()
    }

    // Links to TextView that is added to each row in the RecyclerView
    inner class PokemonViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvPokemonName = view.tv_menu_pokemon_name_item
        val tvPokemonType1 = view.tv_menu_type_item
        val tvPokemonType2 = view.tv_menu_type_item2
    }
}




