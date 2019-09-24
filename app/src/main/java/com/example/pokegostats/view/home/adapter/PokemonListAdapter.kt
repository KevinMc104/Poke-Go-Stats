package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.custom.PokemonStatsRowView
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedActivity
import java.util.*
import kotlin.collections.ArrayList

class PokemonListAdapter(context: Context) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>(), Filterable {
    private val context: Context = context
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon and Pokemon Types
    private var pokemon = emptyList<PokemonFormsTypesWeatherBoosts>()
    private var pokemonFiltered = emptyList<PokemonFormsTypesWeatherBoosts>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonFiltered.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        return PokemonListViewHolder(PokemonStatsRowView(parent.context))
    }

    // Binds each pokemon in the ArrayList to PokemonStatsRowView
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.rowView.setup(pokemonFiltered[position])
        val pokemonId = pokemonFiltered[position].pokemon_id
        val formId = pokemonFiltered[position].FORMS_LIST!![0]
        holder.rowView.setOnClickListener {
            val intent = Intent(context, PokemonDetailedActivity::class.java)
            intent.putExtra(helper.POKEMON_ID, pokemonId.toString())
            intent.putExtra(helper.POKEMON_FORM_ID, formId)
            context.startActivity(intent)
        }
    }

    internal fun setPokemonFormsTypesWeatherBoosts(pokemonFormsTypesWeatherBoosts: List<PokemonFormsTypesWeatherBoosts>) {
        this.pokemon = pokemonFormsTypesWeatherBoosts
        this.pokemonFiltered = pokemonFormsTypesWeatherBoosts
        notifyDataSetChanged()
    }

    inner class PokemonListViewHolder(view: PokemonStatsRowView) : RecyclerView.ViewHolder(view) {
        val rowView = view
    }

    /**
     * Filters on Pokemon Name and Pokemon Type
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val userInputString = charSequence.toString()
                if (userInputString.isEmpty()) {
                    pokemonFiltered = pokemon
                } else {
                    val filteredList = ArrayList<PokemonFormsTypesWeatherBoosts>()

                    // Check each row to see if query matches Pokemon Name or Type
                    for (row in pokemon) {
                        val types = row.TYPES_LIST
                        val pokeName = row.pokemon_name!!.toLowerCase(Locale.getDefault())
                        val strCheck = userInputString.toLowerCase(Locale.getDefault())
                        if (types!!.size == 2) {
                            val type1 = types[1].toLowerCase(Locale.getDefault())
                            if (pokeName.contains(strCheck)
                                || type1.contains(strCheck)) {
                                filteredList.add(row)
                            }
                        } else if (types.size == 4) {
                            val type1 = types[1].toLowerCase(Locale.getDefault())
                            val type2 = types[3].toLowerCase(Locale.getDefault())
                            if (pokeName.contains(strCheck)
                                || type1.contains(strCheck)
                                || type2.contains(strCheck)) {
                                filteredList.add(row)
                            }
                        }
                    }
                    pokemonFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = pokemonFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                pokemonFiltered = filterResults.values as ArrayList<PokemonFormsTypesWeatherBoosts>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }
}




