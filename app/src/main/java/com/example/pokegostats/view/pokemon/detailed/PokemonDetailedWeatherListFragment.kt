package com.example.pokegostats.view.pokemon.detailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonGoApiService
import com.example.pokegostats.service.PokemonHelper
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.pokemon_detailed_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class PokemonDetailedWeatherListFragment : Fragment() {

    // Reference to the RecyclerView adapter
    private lateinit var pokemonDetailedViewModel: PokemonDetailedViewModel
    private val helper: PokemonHelper = PokemonHelper.instance

    /**
     * TODO: Inject this in the repository instead of fragment
     */
    @Inject protected lateinit var service: PokemonGoApiService

    companion object {
        fun newInstance(pokemonId: Int, pokemonFormName: String): PokemonDetailedWeatherListFragment = PokemonDetailedWeatherListFragment().apply {
            val args = Bundle()
            args.putInt(helper.POKEMON_ID, pokemonId)
            args.putString(helper.POKEMON_FORM_NAME, pokemonFormName)
            arguments = args
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.pokemon_detailed_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creates the View Model
        val factory = PokemonDetailedViewModel.Companion.Factory(requireActivity().application, service)
        pokemonDetailedViewModel = ViewModelProvider(this, factory).get(PokemonDetailedViewModel::class.java)
        GlobalScope.launch (Dispatchers.Main) {
            // call out to Repository to get stats
            try {
                pokemonDetailedViewModel.getPokemon(arguments!!.getInt(helper.POKEMON_ID), arguments!!.getString(helper.POKEMON_FORM_NAME).toString())
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        pokemonDetailedViewModel.pokemonDetailed.observe(this, Observer { pokemon ->
            pokemon?.let {
                val weatherBoosts= it.pokemonWeatherBoosts
                // TODO: Make this a recyclerView as the size will always be different
                when(weatherBoosts!!.size) {
                    1 -> {
                        weather_name.setup("Weather Boost", weatherBoosts[0].weatherName.toString(), null, false)
                    }
                    2 -> {
                        weather_name.setup("Weather Boost", weatherBoosts[0].weatherName.toString(), null, false)
                        weather_name2.setup("Weather Boost", weatherBoosts[1].weatherName.toString(), null, false)
                    }
                    3 -> {
                        weather_name.setup("Weather Boost", weatherBoosts[0].weatherName.toString(), null, false)
                        weather_name2.setup("Weather Boost", weatherBoosts[1].weatherName.toString(), null, false)
                        weather_name3.setup("Weather Boost", weatherBoosts[2].weatherName.toString(), null, false)
                    }
                }
            }
        })
    }
}
