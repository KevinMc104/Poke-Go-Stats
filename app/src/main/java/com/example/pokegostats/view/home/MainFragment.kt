package com.example.pokegostats.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokegostats.PokemonAdapter
import com.example.pokegostats.R
import com.example.pokegostats.model.PokemonGoStats
import com.example.pokegostats.pokemon.Pokemon
import com.example.pokegostats.service.PokemonGoApiService
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject protected lateinit var service: PokemonGoApiService

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add pokemon
        var list: ArrayList<String> = ArrayList()
        list.add("something")
//        list = service.getPokemonGoStats()
        service.getPokemonGoStats()
        var pokemonStatsList: ArrayList<String> = ArrayList()

        var iterator = 0
//        for(item in list) {
//            pokemonStatsList[iterator] = item.pokemon_name
//            iterator++
//        }


        // Creates vertical Layout Manager
        rv_pokemon_list.layoutManager = LinearLayoutManager(activity)

        // can use gridlayout too
        // rv_pokemon_list.layoutManager = GridLayoutManager(this, 2)

        rv_pokemon_list.adapter = PokemonAdapter(list, context!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
