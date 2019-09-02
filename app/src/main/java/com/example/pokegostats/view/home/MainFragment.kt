package com.example.pokegostats.view.home

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokegostats.view.home.adapter.PokemonAdapter
import com.example.pokegostats.R
import com.example.pokegostats.model.PokemonGoStats
import com.example.pokegostats.model.exception.NotFoundException
import com.example.pokegostats.service.PokemonGoService
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject protected lateinit var service: PokemonGoService

    // Reference to the RecyclerView adapter
    private lateinit var adapter: PokemonAdapter

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

        // Creates vertical Layout Manager
        rv_pokemon_list.layoutManager = LinearLayoutManager(activity)

        GlobalScope.launch (Dispatchers.Main) {
            // add pokemon
            val list: ArrayList<PokemonGoStats> = ArrayList()

            // call out to endpoint to get stats
            try {
                list.addAll(service.getPokemonGoStats())
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }

            // can use gridlayout too
            // rv_pokemon_list.layoutManager = GridLayoutManager(this, 2)
            rv_pokemon_list.adapter = PokemonAdapter(list, context!!)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
