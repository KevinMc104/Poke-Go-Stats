package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import kotlinx.android.synthetic.main.pokemon_stats_row_menu.view.*

class PokemonAdapter(context: Context) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    // Cached copy of Pokemon and Pokemon Types
    private var pokemonAndFormsAndTypes = emptyList<PokemonAndFormsAndTypes>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonAndFormsAndTypes.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_stats_row_menu, parent, false)
        return PokemonViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        var currentName = pokemonAndFormsAndTypes[position].pokemon!!.pokemonName
        val formName = pokemonAndFormsAndTypes[position].pokemonForm!!.formName
        // Don't add form name if it's Default
        if(!formName.equals("Default")) {
            currentName += "(${formName})"
        }

        var currentType1 = ""
        var currentType2 = ""
        val pokemonTypes = pokemonAndFormsAndTypes[position].pokemonTypes
        if(pokemonTypes!!.size == 1) {
            currentType1 = pokemonTypes!![0].type.toString()
        } else if(pokemonTypes!!.size == 2) {
            currentType1 = pokemonTypes!![0].type.toString()
            currentType2 = pokemonTypes!![1].type.toString()
        }
        holder.tvPokemonName.text = currentName
        holder.tvPokemonType1.text = currentType1
        holder.tvPokemonType2.text = currentType2
    }

    internal fun setPokemonAndFormsAndPokemonTypes(pokemonAndFormsAndTypes: List<PokemonAndFormsAndTypes>) {
        this.pokemonAndFormsAndTypes = pokemonAndFormsAndTypes
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




