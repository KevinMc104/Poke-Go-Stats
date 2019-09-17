package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import com.example.pokegostats.room.entity.PokemonFormsTypesWeatherBoosts
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedActivity
import kotlinx.android.synthetic.main.pokemon_stats_row.view.*

class PokemonListAdapter(context: Context) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context = context
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon and Pokemon Types
    private var pokemonFormsTypesWeatherBoosts = emptyList<PokemonFormsTypesWeatherBoosts>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonFormsTypesWeatherBoosts.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_stats_row, parent, false)
        return PokemonListViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        var currentName = pokemonFormsTypesWeatherBoosts[position].pokemon_name
        currentName = "-$currentName"
        var formName = pokemonFormsTypesWeatherBoosts[position].FORMS_LIST!![1]
        // Don't add form name if it's Default
        if(!formName.equals("Default")) {
            formName = "(${formName})"
        } else {
            formName = ""
        }

        val pokemonTypes = pokemonFormsTypesWeatherBoosts[position].TYPES_LIST!!
        if (pokemonTypes.size == 2) {
            helper.setPokemonTypeLook(context, holder.tvPokemonType1, pokemonTypes[1])
        } else if (pokemonTypes.size == 4) {
            helper.setPokemonTypeLook(context, holder.tvPokemonType1, pokemonTypes[1])
            helper.setPokemonTypeLook(context, holder.tvPokemonType2, pokemonTypes[3])
        }

        holder.tvPokemonId.text = pokemonFormsTypesWeatherBoosts[position].pokemon_id.toString()
        holder.tvPokemonName.text = currentName
        holder.tvPokemonFormName.text = formName
        holder.tvMaxCp.text = pokemonFormsTypesWeatherBoosts[position].max_cp.toString()
    }

    internal fun setPokemonFormsTypesWeatherBoosts(pokemonFormsTypesWeatherBoosts: List<PokemonFormsTypesWeatherBoosts>) {
        this.pokemonFormsTypesWeatherBoosts = pokemonFormsTypesWeatherBoosts
        notifyDataSetChanged()
    }

    // Links to TextView that is added to each row in the RecyclerView
    inner class PokemonListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each pokemon to
        val tvPokemonId = view.tv_menu_id
        val tvPokemonName = view.tv_menu_pokemon_name
        val tvPokemonFormName = view.tv_menu_pokemon_form_name
        val tvMaxCp = view.tv_menu_max_cp
        val tvPokemonType1 = view.tv_menu_type1
        val tvPokemonType2 = view.tv_menu_type2
        val clickListener = view.setOnClickListener {
            val intent = Intent(context, PokemonDetailedActivity::class.java)
            intent.putExtra(helper.POKEMON_ID, tvPokemonId.text.toString())
            // remove parenthesis and pass form name
            var formName = tvPokemonFormName.text.toString()
            if(!formName.isNullOrBlank()) {
                formName = formName.substring(formName.indexOf("(")+1,formName.indexOf(")"))
                intent.putExtra(helper.POKEMON_FORM_NAME, formName)
            } else {
                intent.putExtra(helper.POKEMON_FORM_NAME, "Default")
            }
            context.startActivity(intent)
        }
    }
}




