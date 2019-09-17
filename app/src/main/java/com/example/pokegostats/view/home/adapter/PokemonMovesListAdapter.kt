package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.R
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.move.detailed.PokemonMoveDetailedActivity
import kotlinx.android.synthetic.main.pokemon_moves_row.view.*

class PokemonMovesListAdapter(context: Context) : RecyclerView.Adapter<PokemonMovesListAdapter.PokemonMovesViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context = context
    private val helper: PokemonHelper = PokemonHelper.instance

    // Cached copy of Pokemon Moves
    private var pokemonMoves = emptyList<PokemonMovesEntity>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return pokemonMoves.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonMovesViewHolder {
        val itemView = inflater.inflate(R.layout.pokemon_moves_row, parent, false)
        return PokemonMovesViewHolder(itemView)
    }

    // Binds each pokemon in the ArrayList to a view
    override fun onBindViewHolder(holder: PokemonMovesViewHolder, position: Int) {
        holder.tvMoveName.text = pokemonMoves[position].name
        holder.tvPower.text = pokemonMoves[position].power.toString()
        helper.setPokemonTypeLook(context, holder.tvMoveType, pokemonMoves[position].typeName)
    }

    internal fun setPokemonMoves(pokemonMoves: List<PokemonMovesEntity>) {
        this.pokemonMoves = pokemonMoves
        notifyDataSetChanged()
    }

    // Links to TextView that is added to each row in the RecyclerView
    inner class PokemonMovesViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each move to
        val tvMoveName = view.tv_menu_move_name
        val tvPower = view.tv_menu_power
        val tvMoveType = view.tv_menu_type
        val clickListener = view.setOnClickListener {
            val intent = Intent(context, PokemonMoveDetailedActivity::class.java)
            intent.putExtra(helper.POKEMON_MOVE_NAME, tvMoveName.text.toString())
            context.startActivity(intent)
        }
    }
}