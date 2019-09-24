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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
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
        inflater!!.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
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