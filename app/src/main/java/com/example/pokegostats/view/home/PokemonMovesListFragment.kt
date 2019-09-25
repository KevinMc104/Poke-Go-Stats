package com.example.pokegostats.view.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokegostats.R
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.view.home.adapter.PokemonMovesListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.pokemon_moves_fragment.*
import javax.inject.Inject

class PokemonMovesListFragment : Fragment(), SearchView.OnQueryTextListener {

    // Reference to the RecyclerView adapter
    private lateinit var adapter: PokemonMovesListAdapter
    private lateinit var viewModel: PokemonMovesListFragmentViewModel
    @Inject lateinit var repository: PokemonGoStatsRepository

    companion object {
        fun newInstance() = PokemonMovesListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        // Creates the View Model
        val factory = PokemonMovesListFragmentViewModel.Companion.Factory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory).get(PokemonMovesListFragmentViewModel::class.java)

        return inflater.inflate(R.layout.pokemon_moves_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.move_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //A String for the message to be displayed in a Toast
        var msg: String
        //Switch and case on the MenuItem object's id
        when (item.itemId) {
            R.id.sort_default -> {
                msg = "Default Sorting"
                adapter.sortDefault()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_move_name_desc -> {
                msg = "sorting by Move Name Descending..."
                adapter.sortMoveNameDescending()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_power_max_min -> {
                msg = "sorting by Power Max->Min..."
                adapter.sortPowerMaxMin()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_power_min_max -> {
                msg = "sorting by Power Min->Max..."
                adapter.sortPowerMinMax()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.move_default_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Resetting Filter..."
                    adapter.filterByType(getString(R.string.reset))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_normal_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Normal Type..."
                    adapter.filterByType(getString(R.string.normal))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_fire_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Fire Type..."
                    adapter.filterByType(getString(R.string.fire))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_water_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Water Type..."
                    adapter.filterByType(getString(R.string.water))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_electric_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Electric Type..."
                    adapter.filterByType(getString(R.string.electric))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_grass_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Grass Type..."
                    adapter.filterByType(getString(R.string.grass))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_ice_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Ice Type..."
                    adapter.filterByType(getString(R.string.ice))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_fighting_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Fighting Type..."
                    adapter.filterByType(getString(R.string.fighting))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_poison_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Poison Type..."
                    adapter.filterByType(getString(R.string.poison))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_ground_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Ground Type..."
                    adapter.filterByType(getString(R.string.ground))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_flying_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Flying Type..."
                    adapter.filterByType(getString(R.string.flying))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_psychic_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Psychic Type..."
                    adapter.filterByType(getString(R.string.psychic))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_bug_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Bug Type..."
                    adapter.filterByType(getString(R.string.bug))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_rock_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Rock Type..."
                    adapter.filterByType(getString(R.string.rock))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_ghost_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Ghost Type..."
                    adapter.filterByType(getString(R.string.ghost))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_dragon_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Dragon Type..."
                    adapter.filterByType(getString(R.string.dragon))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_dark_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Dark Type..."
                    adapter.filterByType(getString(R.string.dark))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_steel_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Steel Type..."
                    adapter.filterByType(getString(R.string.steel))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.move_fairy_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                } else {
                    item.isChecked = true
                    msg = "Filtering by Fairy Type..."
                    adapter.filterByType(getString(R.string.fairy))
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
        }
        adapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextChange(query: String): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // adapter to the list
        adapter = PokemonMovesListAdapter(context!!)
        rv_pokemon_moves_list.adapter = adapter

        // Creates vertical Layout Manager
        rv_pokemon_moves_list.layoutManager = LinearLayoutManager(activity)

        viewModel.allPokemonMoves.observe(this, Observer { move ->
            // Update the cached copy of the words in the adapter.
            move?.let { adapter.setPokemonMoves(it) }
        })
    }
}