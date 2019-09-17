package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.custom.PokemonStatsRowView
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedActivity

class PokemonListAdapter(context: Context) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {
    private val context: Context = context
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon and Pokemon Types
    private var pokemonFormsTypesWeatherBoosts = emptyList<PokemonFormsTypesWeatherBoosts>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonFormsTypesWeatherBoosts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        return PokemonListViewHolder(PokemonStatsRowView(parent.context))
    }

    // Binds each pokemon in the ArrayList to PokemonStatsRowView
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.rowView.setup(pokemonFormsTypesWeatherBoosts[position])
        val pokemonId = pokemonFormsTypesWeatherBoosts[position].pokemon_id
        val formId = pokemonFormsTypesWeatherBoosts[position].FORMS_LIST!![0]
        holder.rowView.setOnClickListener {
            val intent = Intent(context, PokemonDetailedActivity::class.java)
            intent.putExtra(helper.POKEMON_ID, pokemonId.toString())
            intent.putExtra(helper.POKEMON_FORM_ID, formId)
            context.startActivity(intent)
        }
    }

    internal fun setPokemonFormsTypesWeatherBoosts(pokemonFormsTypesWeatherBoosts: List<PokemonFormsTypesWeatherBoosts>) {
        this.pokemonFormsTypesWeatherBoosts = pokemonFormsTypesWeatherBoosts
        notifyDataSetChanged()
    }

    inner class PokemonListViewHolder(view: PokemonStatsRowView) : RecyclerView.ViewHolder(view) {
        val rowView = view
    }
}




