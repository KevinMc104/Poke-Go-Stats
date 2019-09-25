package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.custom.PokemonStatsRowHeaderView
import com.example.pokegostats.view.custom.PokemonStatsRowView
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedActivity
import java.util.*
import kotlin.collections.ArrayList

class PokemonListAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private val helper: PokemonHelper = PokemonHelper.instance
    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    private var normalFilter: Boolean = false
    private var fireFilter: Boolean = false
    private var waterFilter: Boolean = false
    private var electricFilter: Boolean = false
    private var grassFilter: Boolean = false
    private var iceFilter: Boolean = false
    private var fightingFilter: Boolean = false
    private var poisonFilter: Boolean = false
    private var groundFilter: Boolean = false
    private var flyingFilter: Boolean = false
    private var psychicFilter: Boolean = false
    private var bugFilter: Boolean = false
    private var rockFilter: Boolean = false
    private var ghostFilter: Boolean = false
    private var dragonFilter: Boolean = false
    private var darkFilter: Boolean = false
    private var steelFilter: Boolean = false
    private var fairyFilter: Boolean = false

    // Cached copy of Pokemon and Pokemon Types
    private var pokemon: MutableList<PokemonFormsTypesWeatherBoosts> = mutableListOf()
    private var pokemonFiltered: MutableList<PokemonFormsTypesWeatherBoosts> = mutableListOf()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonFiltered.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER) {
            return PokemonHeaderViewHolder(PokemonStatsRowHeaderView(parent.context))
        }
        return PokemonListViewHolder(PokemonStatsRowView(parent.context))
    }

    // Binds each pokemon in the ArrayList to PokemonStatsRowView
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PokemonListViewHolder) {
            val currItem = getItem(position)
            holder.rowView.setup(currItem)
            val pokemonId = currItem.pokemon_id
            val formId = currItem.FORMS_LIST!![0]
            holder.rowView.setOnClickListener {
                val intent = Intent(context, PokemonDetailedActivity::class.java)
                intent.putExtra(helper.POKEMON_ID, pokemonId.toString())
                intent.putExtra(helper.POKEMON_FORM_ID, formId)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position)) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private fun getItem(position: Int): PokemonFormsTypesWeatherBoosts {
        return pokemonFiltered[position - 1]
    }

    internal fun setPokemonFormsTypesWeatherBoosts(pokemon: List<PokemonFormsTypesWeatherBoosts>) {
        this.pokemon = pokemon.toMutableList()
        this.pokemonFiltered = pokemon.toMutableList()
        notifyDataSetChanged()
    }

    fun sortDefault() {
        pokemon = pokemon.sortedWith(compareBy(PokemonFormsTypesWeatherBoosts::pokemon_id)).toMutableList()
        pokemonFiltered = pokemonFiltered.sortedWith(compareBy(PokemonFormsTypesWeatherBoosts::pokemon_id)).toMutableList()
    }

    fun sortPokeNameAscending() {
        pokemon = pokemon.sortedWith(compareBy(PokemonFormsTypesWeatherBoosts::pokemon_name)).toMutableList()
        pokemonFiltered = pokemonFiltered.sortedWith(compareBy(PokemonFormsTypesWeatherBoosts::pokemon_name)).toMutableList()
    }

    fun sortPokeNameDescending() {
        pokemon = pokemon.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER){it.pokemon_name.toString()}).toMutableList()
        pokemonFiltered = pokemonFiltered.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER){it.pokemon_name.toString()}).toMutableList()
    }

    fun sortMaxCpMaxMin() {
        pokemon = pokemon.sortedWith(compareByDescending{it.max_cp}).toMutableList()
        pokemonFiltered = pokemonFiltered.sortedWith(compareByDescending{it.max_cp}).toMutableList()
    }

    fun sortMaxCpMinMax() {
        pokemon = pokemon.sortedWith(compareBy(PokemonFormsTypesWeatherBoosts::max_cp)).toMutableList()
        pokemonFiltered = pokemonFiltered.sortedWith(compareBy(PokemonFormsTypesWeatherBoosts::max_cp)).toMutableList()
    }

    fun filterByType(filter: String, filterFlag: Boolean) {
        val filteredList = ArrayList<PokemonFormsTypesWeatherBoosts>()
        setFilterFlag(filter, filterFlag)
        val activeFilters = getActiveFilters()
        when {
            activeFilters.isNullOrEmpty() -> {
                pokemonFiltered = pokemon
                notifyDataSetChanged()
            }
            activeFilters.size == 1 -> {
                for(item in pokemon) {
                    if(hasType(item.TYPES_LIST!!, activeFilters[0])) filteredList.add(item)
                }
                pokemonFiltered = filteredList
                notifyDataSetChanged()
            }
            else -> {
                for(item in pokemon) {
                    // Ignore any pokemon that don't have 2 types
                    if(item.TYPES_LIST!!.size == 4) {
                        if(checkBothTypes(activeFilters, item.TYPES_LIST!![1], item.TYPES_LIST!![3])) {
                            filteredList.add(item)
                        }
                    }
                }
                pokemonFiltered = filteredList
                notifyDataSetChanged()
            }
        }
    }

    private fun checkBothTypes(filters: List<String>, type1: String, type2: String): Boolean {
        var returnCheck = false
        var firstCheck = false
        var secondCheck = false
        for(filter in filters) {
            if(type1 == filter) {
                firstCheck = true
            }
            if(type2 == filter) {
                secondCheck = true
            }
        }
        if(firstCheck && secondCheck) {
            returnCheck = true
        }
        return returnCheck
    }

    private fun hasType(list: List<String>, filter: String): Boolean {
        for(item in list) {
            if(item == filter) {
                return true
            }
        }
        return false
    }

    private fun setFilterFlag(type: String, isActive: Boolean) {
        when(type) {
            context.getString(R.string.normal) -> normalFilter = isActive
            context.getString(R.string.fire) -> fireFilter = isActive
            context.getString(R.string.water) -> waterFilter = isActive
            context.getString(R.string.electric) -> electricFilter = isActive
            context.getString(R.string.grass) -> grassFilter = isActive
            context.getString(R.string.ice) -> iceFilter = isActive
            context.getString(R.string.fighting) -> fightingFilter = isActive
            context.getString(R.string.poison) -> poisonFilter = isActive
            context.getString(R.string.ground) -> groundFilter = isActive
            context.getString(R.string.flying) -> flyingFilter = isActive
            context.getString(R.string.psychic) -> psychicFilter = isActive
            context.getString(R.string.bug) -> bugFilter = isActive
            context.getString(R.string.rock) -> rockFilter = isActive
            context.getString(R.string.ghost) -> ghostFilter = isActive
            context.getString(R.string.dragon) -> dragonFilter = isActive
            context.getString(R.string.dark) -> darkFilter = isActive
            context.getString(R.string.steel) -> steelFilter = isActive
            context.getString(R.string.fairy) -> fairyFilter = isActive
        }
    }

    private fun getActiveFilters(): List<String> {
        val list = ArrayList<String>()
        if(normalFilter) list.add(context.getString(R.string.normal))
        if(fireFilter) list.add(context.getString(R.string.fire))
        if(waterFilter) list.add(context.getString(R.string.water))
        if(electricFilter) list.add(context.getString(R.string.electric))
        if(grassFilter) list.add(context.getString(R.string.grass))
        if(iceFilter) list.add(context.getString(R.string.ice))
        if(fightingFilter) list.add(context.getString(R.string.fighting))
        if(poisonFilter) list.add(context.getString(R.string.poison))
        if(groundFilter) list.add(context.getString(R.string.ground))
        if(flyingFilter) list.add(context.getString(R.string.flying))
        if(psychicFilter) list.add(context.getString(R.string.psychic))
        if(bugFilter) list.add(context.getString(R.string.bug))
        if(rockFilter) list.add(context.getString(R.string.rock))
        if(ghostFilter) list.add(context.getString(R.string.ghost))
        if(dragonFilter) list.add(context.getString(R.string.dragon))
        if(darkFilter) list.add(context.getString(R.string.dark))
        if(steelFilter) list.add(context.getString(R.string.steel))
        if(fairyFilter) list.add(context.getString(R.string.fairy))

        return list
    }

    inner class PokemonListViewHolder(view: PokemonStatsRowView) : RecyclerView.ViewHolder(view) {
        val rowView = view
    }

    inner class PokemonHeaderViewHolder(view: PokemonStatsRowHeaderView) : RecyclerView.ViewHolder(view)

    /**
     * Search Filters on Pokemon Name and Pokemon Type
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




