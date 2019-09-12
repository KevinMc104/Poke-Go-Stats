package com.example.pokegostats.view.pokemon.detailed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.room.entity.PokemonWeatherBoostsEntity
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.custom.PokemonDetailedRowView

class PokemonDetailedWeatherListAdapter(context: Context) : RecyclerView.Adapter<PokemonDetailedWeatherListAdapter.PokemonDetailedWeatherListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context = context
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon Moves
    private var weatherBoosts = emptyList<PokemonWeatherBoostsEntity>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return weatherBoosts.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonDetailedWeatherListViewHolder {
        return PokemonDetailedWeatherListViewHolder(PokemonDetailedRowView(parent.context))
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonDetailedWeatherListViewHolder, position: Int) {
        holder.rowView.setup("Weather Boost", weatherBoosts[position].weatherName.toString(), null, false)
    }

    internal fun setWeatherBoosts(weatherBoosts: List<PokemonWeatherBoostsEntity>) {
        this.weatherBoosts = weatherBoosts
        notifyDataSetChanged()
    }

    inner class PokemonDetailedWeatherListViewHolder (view: PokemonDetailedRowView) : RecyclerView.ViewHolder(view)
    {
        val rowView = view
    }
}