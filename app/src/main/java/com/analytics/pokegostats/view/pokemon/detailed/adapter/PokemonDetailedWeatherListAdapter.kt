package com.analytics.pokegostats.view.pokemon.detailed.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.analytics.pokegostats.view.custom.PokemonDetailedWeatherRowView

class PokemonDetailedWeatherListAdapter(context: Context) : RecyclerView.Adapter<PokemonDetailedWeatherListAdapter.PokemonDetailedWeatherListViewHolder>() {

    // Cached copy of Pokemon Moves
    private var weatherBoosts = emptyList<String>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return weatherBoosts.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonDetailedWeatherListViewHolder {
        return PokemonDetailedWeatherListViewHolder(PokemonDetailedWeatherRowView(parent.context))
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonDetailedWeatherListViewHolder, position: Int) {
        holder.rowView.setup(weatherBoosts[position])
    }

    internal fun setWeatherBoosts(weatherBoosts: List<String>) {
        this.weatherBoosts = weatherBoosts
        notifyDataSetChanged()
    }

    inner class PokemonDetailedWeatherListViewHolder (view: PokemonDetailedWeatherRowView) : RecyclerView.ViewHolder(view) {
        val rowView = view
    }
}