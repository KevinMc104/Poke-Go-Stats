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
import com.example.pokegostats.view.home.adapter.PokemonListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.pokemon_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class PokemonListFragment : Fragment(), SearchView.OnQueryTextListener {

    // Reference to the RecyclerView adapter
    private lateinit var adapter: PokemonListAdapter
    private lateinit var viewModel: PokemonListFragmentViewModel
    @Inject lateinit var repository: PokemonGoStatsRepository

    companion object {
        fun newInstance() = PokemonListFragment()
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
        val factory = PokemonListFragmentViewModel.Companion.Factory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory).get(PokemonListFragmentViewModel::class.java)

        return inflater.inflate(R.layout.pokemon_list_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.pokemon_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //A String for the message to be displayed in a Toast
        var msg = ""
        //Switch and case on the MenuItem object's id
        when (item.itemId) {
            R.id.sort_default -> {
                msg = "Default Sorting"
                adapter.sortDefault()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_poke_name_asc -> {
                msg = "Sorting by Pokemon Name Ascending..."
                adapter.sortPokeNameAscending()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_poke_name_desc -> {
                msg = "Sorting by Pokemon Name Descending..."
                adapter.sortPokeNameDescending()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_max_cp_max_min -> {
                msg = "Sorting by Max CP Max->Min..."
                adapter.sortMaxCpMaxMin()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.sort_max_cp_min_max -> {
                msg = "Sorting by Max CP Min->Max..."
                adapter.sortMaxCpMinMax()
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
            }
            R.id.pokemon_normal_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.normal), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Normal Type..."
                    adapter.filterByType(getString(R.string.normal), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_fire_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.fire), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Fire Type..."
                    adapter.filterByType(getString(R.string.fire), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_water_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.water), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Water Type..."
                    adapter.filterByType(getString(R.string.water), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_electric_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.electric), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Electric Type..."
                    adapter.filterByType(getString(R.string.electric), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_grass_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.grass), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Grass Type..."
                    adapter.filterByType(getString(R.string.grass), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_ice_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.ice), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Ice Type..."
                    adapter.filterByType(getString(R.string.ice), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_fighting_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.fighting), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Fighting Type..."
                    adapter.filterByType(getString(R.string.fighting), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_poison_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.poison), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Poison Type..."
                    adapter.filterByType(getString(R.string.poison), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_ground_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.ground), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Ground Type..."
                    adapter.filterByType(getString(R.string.ground), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_flying_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.flying), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Flying Type..."
                    adapter.filterByType(getString(R.string.flying), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_psychic_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.psychic), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Psychic Type..."
                    adapter.filterByType(getString(R.string.psychic), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_bug_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.bug), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Bug Type..."
                    adapter.filterByType(getString(R.string.bug), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_rock_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.rock), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Rock Type..."
                    adapter.filterByType(getString(R.string.rock), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_ghost_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.ghost), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Ghost Type..."
                    adapter.filterByType(getString(R.string.ghost), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_dragon_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.dragon), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Dragon Type..."
                    adapter.filterByType(getString(R.string.dragon), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_dark_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.dark), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Dark Type..."
                    adapter.filterByType(getString(R.string.dark), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_steel_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.steel), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Steel Type..."
                    adapter.filterByType(getString(R.string.steel), true)
                    Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
                }
            }
            R.id.pokemon_fairy_filter -> {
                if(item.isChecked) {
                    item.isChecked = false
                    adapter.filterByType(getString(R.string.fairy), false)
                } else {
                    item.isChecked = true
                    msg = "Filtering by Fairy Type..."
                    adapter.filterByType(getString(R.string.fairy), true)
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
        adapter = PokemonListAdapter(context!!)
        rv_pokemon_list.adapter = adapter

        // Creates vertical Layout Manager
        rv_pokemon_list.layoutManager = LinearLayoutManager(activity)
        GlobalScope.launch (Dispatchers.Main) {
            // call out to Repository to get stats
            try {
                viewModel.populateAllTables()
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.allPokemonFormsTypesWeatherBoosts.observe(this, Observer { pokemon ->
            pokemon?.let {
                adapter.setPokemonFormsTypesWeatherBoosts(pokemon)
            }
        })
    }
}
