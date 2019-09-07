package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonAndPokemonForms
import com.example.pokegostats.room.entity.PokemonFormsAndPokemonTypes
import kotlinx.android.synthetic.main.pokemon_stats_row_menu.view.*

class PokemonAdapter(context: Context) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    // Cached copy of Pokemon and Pokemon Forms
    private var pokemonAndPokemonForms = emptyList<PokemonAndPokemonForms>()

    // Cached copy of Pokemon and Pokemon Types
    private var pokemonFormsAndPokemonTypes = emptyList<PokemonFormsAndPokemonTypes>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonAndPokemonForms.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_stats_row_menu, parent, false)
        return PokemonViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        var current = pokemonAndPokemonForms[position].pokemon!!.pokemonName

        var currentType1 = ""
        var currentType2 = ""
        if(pokemonAndPokemonForms[position].pokemonForms!!.size == 1) {
            val formName = pokemonAndPokemonForms[position].pokemonForms!![0]
            if(!formName.formName.equals("Default")) {
                current += "(${formName.formName})"
            }
            // NOTE: There should never be more than a couple forms and types per pokemon
            // Get Types of current Form
            // Pokemon can have at max 2 Types per Form
            var currentFormId = formName.id
            for(item in pokemonFormsAndPokemonTypes) {
                if (currentFormId == item.pokemonForm!!.id) {
                    if(item.pokemonTypes!!.size == 1) {
                        currentType1 = item.pokemonTypes!![0].type.toString()
                    } else {
                        currentType1 = item.pokemonTypes!![0].type.toString()
                        currentType2 = item.pokemonTypes!![1].type.toString()
                    }
                }
            }
        } else if(pokemonAndPokemonForms[position].pokemonForms!!.size > 1) {
            // TODO: What to do when multiple forms?
        }
        holder.tvPokemonName.text = current
        holder.tvPokemonType1.text = currentType1
        holder.tvPokemonType2.text = currentType2
    }

    internal fun setPokemonAndPokemonForms(pokemonAndPokemonForms: List<PokemonAndPokemonForms>) {
        this.pokemonAndPokemonForms = pokemonAndPokemonForms
        notifyDataSetChanged()
    }

    internal fun setPokemonFormsAndPokemonTypes(pokemonFormsAndPokemonTypes: List<PokemonFormsAndPokemonTypes>) {
        this.pokemonFormsAndPokemonTypes = pokemonFormsAndPokemonTypes
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




