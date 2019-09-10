package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonAndFormsAndTypes
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.pokemon.detailed.PokemonDetailedActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.pokemon_stats_row.view.*

class PokemonListAdapter(context: Context) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {
    private val context: Context = context
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon and Pokemon Types
    private var pokemonAndFormsAndTypes = emptyList<PokemonAndFormsAndTypes>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonAndFormsAndTypes.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_stats_row, parent, false)
        return PokemonListViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        var currentName = pokemonAndFormsAndTypes[position].pokemon!!.pokemonName
        val formName = pokemonAndFormsAndTypes[position].pokemonForm!!.formName
        // Don't add form name if it's Default
        if(!formName.equals("Default")) {
            currentName += "(${formName})"
        }

        var currentType1 = ""
        var currentType2 = ""
        val pokemonTypes = pokemonAndFormsAndTypes[position].pokemonTypes

        // TODO: Figure out how to make types look like fancy ovals instead of squares
//        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.rounded_type1)
//        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
//
//        val unwrappedDrawable2 = AppCompatResources.getDrawable(context, R.drawable.rounded_type2)
//        val wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2!!)

        if(pokemonTypes!!.size == 1) {
            currentType1 = pokemonTypes!![0].type.toString()
            holder.tvPokemonType1.setTextColor(Color.WHITE)
            holder.tvPokemonType1.setBackgroundColor(helper.setColorByType(currentType1))
//            DrawableCompat.setTint(wrappedDrawable, helper.setColorByType(currentType1))
        } else if(pokemonTypes!!.size == 2) {
            currentType1 = pokemonTypes!![0].type.toString()
            currentType2 = pokemonTypes!![1].type.toString()
            holder.tvPokemonType1.setTextColor(Color.WHITE)
            holder.tvPokemonType1.setBackgroundColor(helper.setColorByType(currentType1))
            holder.tvPokemonType2.setTextColor(Color.WHITE)
            holder.tvPokemonType2.setBackgroundColor(helper.setColorByType(currentType2))
//            DrawableCompat.setTint(wrappedDrawable, helper.setColorByType(currentType1))
//            DrawableCompat.setTint(wrappedDrawable2, helper.setColorByType(currentType2))
        }

        holder.tvPokemonId.text = pokemonAndFormsAndTypes[position].pokemon!!.pokemonId.toString()
        holder.tvPokemonName.text = currentName
        holder.tvMaxCp.text = pokemonAndFormsAndTypes[position].pokemon!!.maxCp.toString()
        holder.tvPokemonType1.text = currentType1
        holder.tvPokemonType2.text = currentType2
    }

    internal fun setPokemonAndFormsAndPokemonTypes(pokemonAndFormsAndTypes: List<PokemonAndFormsAndTypes>) {
        this.pokemonAndFormsAndTypes = pokemonAndFormsAndTypes
        notifyDataSetChanged()
    }

    // Links to TextView that is added to each row in the RecyclerView
    inner class PokemonListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each pokemon to
        val tvPokemonId = view.tv_menu_id
        val tvPokemonName = view.tv_menu_pokemon_name
        val tvMaxCp = view.tv_menu_max_cp
        val tvPokemonType1 = view.tv_menu_type1
        val tvPokemonType2 = view.tv_menu_type2
        val clickListener = view.setOnClickListener {
                Snackbar.make(view, tvPokemonName.text.toString(), Snackbar.LENGTH_LONG).show()
                val intent = Intent(context, PokemonDetailedActivity::class.java)
                intent.putExtra("pokemonId", tvPokemonId.text.toString())
                context.startActivity(intent)
        }
    }
}




