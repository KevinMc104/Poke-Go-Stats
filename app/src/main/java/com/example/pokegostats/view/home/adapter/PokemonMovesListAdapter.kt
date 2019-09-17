package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.custom.PokemonMovesRowView
import com.example.pokegostats.view.move.detailed.PokemonMoveDetailedActivity

class PokemonMovesListAdapter(context: Context) : RecyclerView.Adapter<PokemonMovesListAdapter.PokemonMovesViewHolder>() {
    private val context: Context = context
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon Moves
    private var pokemonMoves = emptyList<PokemonMovesEntity>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonMoves.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonMovesViewHolder {
        return PokemonMovesViewHolder(PokemonMovesRowView(parent.context))
    }

    // Binds each move in the ArrayList to PokemonMovesRowView
    override fun onBindViewHolder(holder: PokemonMovesViewHolder, position: Int) {
        holder.rowView.setup(pokemonMoves[position])
        holder.rowView.setOnClickListener {
            val intent = Intent(context, PokemonMoveDetailedActivity::class.java)
            intent.putExtra(helper.POKEMON_MOVE_NAME, pokemonMoves[position].name)
            context.startActivity(intent)
        }
    }

    internal fun setPokemonMoves(pokemonMoves: List<PokemonMovesEntity>) {
        this.pokemonMoves = pokemonMoves
        notifyDataSetChanged()
    }

    inner class PokemonMovesViewHolder (view: PokemonMovesRowView) : RecyclerView.ViewHolder(view) {
        val rowView = view
    }
}