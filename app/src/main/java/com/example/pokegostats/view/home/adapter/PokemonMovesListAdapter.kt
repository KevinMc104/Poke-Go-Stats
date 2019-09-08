package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.view.home.PokemonHelper
import kotlinx.android.synthetic.main.pokemon_moves_row_menu.view.*

class PokemonMovesListAdapter(context: Context) : RecyclerView.Adapter<PokemonMovesListAdapter.PokemonMovesViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon Moves
    private var pokemonMoves = emptyList<PokemonMovesEntity>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonMoves.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonMovesViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_moves_row_menu, parent, false)
        return PokemonMovesViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonMovesViewHolder, position: Int) {
        var name = pokemonMoves[position].name
        val power = pokemonMoves[position].power.toString()
        var type = pokemonMoves[position].type
        holder.tvMoveName.text = name
        holder.tvPower.text = power

        holder.tvMoveType.setTextColor(Color.WHITE)
        holder.tvMoveType.setBackgroundColor(helper.setColorByType(type))
        holder.tvMoveType.text = type
    }

    internal fun setPokemonMoves(pokemonMoves: List<PokemonMovesEntity>) {
        this.pokemonMoves = pokemonMoves
        notifyDataSetChanged()
    }

    // Links to TextView that is added to each row in the RecyclerView
    inner class PokemonMovesViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvMoveName = view.tv_menu_move_name
        val tvPower = view.tv_menu_power
        val tvMoveType = view.tv_menu_type
    }
}