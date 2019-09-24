package com.example.pokegostats.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.pokegostats.room.entity.PokemonMovesEntity
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.custom.PokemonMovesRowHeaderView
import com.example.pokegostats.view.custom.PokemonMovesRowView
import com.example.pokegostats.view.move.detailed.PokemonMoveDetailedActivity
import java.util.*
import kotlin.collections.ArrayList

class PokemonMovesListAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private val helper: PokemonHelper = PokemonHelper.instance
    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    // Cached copy of Pokemon Moves
    private var moves = emptyList<PokemonMovesEntity>()
    private var movesFiltered = emptyList<PokemonMovesEntity>()

    // Gets the number of pokemon in the list
    override fun getItemCount(): Int {
        return movesFiltered.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER) {
            return PokemonMovesHeaderViewHolder(PokemonMovesRowHeaderView(parent.context))
        }
        return PokemonMovesViewHolder(PokemonMovesRowView(parent.context))
    }

    // Binds each move in the ArrayList to PokemonMovesRowView
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PokemonMovesViewHolder) {
            val currItem = getItem(position)
            holder.rowView.setup(currItem)
            holder.rowView.setOnClickListener {
                val intent = Intent(context, PokemonMoveDetailedActivity::class.java)
                intent.putExtra(helper.POKEMON_MOVE_NAME, currItem.name)
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

    private fun getItem(position: Int): PokemonMovesEntity {
        return movesFiltered[position - 1]
    }

    internal fun setPokemonMoves(moves: List<PokemonMovesEntity>) {
        this.moves = moves.sortedWith(compareBy(PokemonMovesEntity::name)).toMutableList()
        this.movesFiltered = moves.sortedWith(compareBy(PokemonMovesEntity::name)).toMutableList()
        notifyDataSetChanged()
    }

    fun sortDefault() {
        moves = moves.sortedWith(compareBy(PokemonMovesEntity::name)).toMutableList()
        movesFiltered = movesFiltered.sortedWith(compareBy(PokemonMovesEntity::name)).toMutableList()
    }

    fun sortMoveNameDescending() {
        moves = moves.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER){it.name}).toMutableList()
        movesFiltered = movesFiltered.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER){it.name}).toMutableList()
    }

    fun sortPowerMaxMin() {
        moves = moves.sortedWith(compareByDescending{it.power}).toMutableList()
        movesFiltered = movesFiltered.sortedWith(compareByDescending{it.power}).toMutableList()
    }

    fun sortPowerMinMax() {
        moves = moves.sortedWith(compareBy(PokemonMovesEntity::power)).toMutableList()
        movesFiltered = movesFiltered.sortedWith(compareBy(PokemonMovesEntity::power)).toMutableList()
    }

    inner class PokemonMovesViewHolder (view: PokemonMovesRowView) : RecyclerView.ViewHolder(view) {
        val rowView = view
    }

    inner class PokemonMovesHeaderViewHolder(view: PokemonMovesRowHeaderView) : RecyclerView.ViewHolder(view)

    /**
     * Filters on Move Name and Move Type
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val userInputString = charSequence.toString()
                if (userInputString.isEmpty()) {
                    movesFiltered = moves
                } else {
                    val filteredList = ArrayList<PokemonMovesEntity>()

                    // Check each row to see if query matches Pokemon Name or Type
                    for (row in moves) {
                        val type = row.typeName.toLowerCase(Locale.getDefault())
                        val moveName = row.name.toLowerCase(Locale.getDefault())
                        val strCheck = userInputString.toLowerCase(Locale.getDefault())
                        if (moveName.contains(strCheck) || type.contains(strCheck)) {
                            filteredList.add(row)
                        }
                    }
                    movesFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = movesFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                movesFiltered = filterResults.values as ArrayList<PokemonMovesEntity>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }
}