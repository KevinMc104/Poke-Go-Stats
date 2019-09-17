package com.example.pokegostats.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonGoApiService
import com.example.pokegostats.view.home.adapter.PokemonListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.pokemon_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    // Reference to the RecyclerView adapter
    private lateinit var adapter: PokemonListAdapter
    private lateinit var mainViewModel: MainViewModel

    /**
     * TODO: Inject this in the repository instead of fragment
     */
    @Inject protected lateinit var service: PokemonGoApiService

    companion object {
        fun newInstance() = PokemonListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Implement Search Menu with search functionality on RecyclerView
//        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.pokemon_list_fragment, container, false)
    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater!!.inflate(R.menu.main_menu, menu)
//        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
//        searchItem.expandActionView()
//        val searchView = searchItem.actionView as SearchView
//
//    }

//    override fun onQueryTextChange(query: String) {
//
//    }
//
//    override fun onQueryTextSubmit(query: String) {
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // adapter to the list
        adapter = PokemonListAdapter(context!!)
        rv_pokemon_list.adapter = adapter

        // Creates vertical Layout Manager
        rv_pokemon_list.layoutManager = LinearLayoutManager(activity)

        // Creates the View Model
        val factory = MainViewModel.Companion.Factory(requireActivity().application, service)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        GlobalScope.launch (Dispatchers.Main) {
            // call out to Repository to get stats
            try {
                mainViewModel.populatePokemonTable()
                mainViewModel.updateDetailedPokemonData()
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        mainViewModel.allPokemonFormsTypesWeatherBoosts.observe(this, Observer { pokemon ->
            pokemon?.let {
                adapter.setPokemonFormsTypesWeatherBoosts(pokemon)
            }
        })
    }
}
