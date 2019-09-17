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
import kotlinx.android.synthetic.main.pokemon_detailed_stats_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class PokemonDetailedListFragment : Fragment() {

    // Reference to the RecyclerView adapter
    private lateinit var pokemonDetailedViewModel: PokemonDetailedViewModel
    private val helper: PokemonHelper = PokemonHelper.instance

    /**
     * TODO: Inject this in the repository instead of fragment
     */
    @Inject protected lateinit var service: PokemonGoApiService

    companion object {
        fun newInstance(pokemonId: Int, formId: Int): PokemonDetailedListFragment = PokemonDetailedListFragment().apply {
            val args = Bundle()
            args.putInt(helper.POKEMON_ID, pokemonId)
            args.putInt(helper.POKEMON_FORM_ID, formId)
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
        return inflater.inflate(R.layout.pokemon_detailed_stats_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creates the View Model
        val factory = PokemonDetailedViewModel.Companion.Factory(requireActivity().application, service)
        pokemonDetailedViewModel = ViewModelProvider(this, factory).get(PokemonDetailedViewModel::class.java)
        GlobalScope.launch (Dispatchers.Main) {
            // call out to Repository to get stats
            try {
                pokemonDetailedViewModel.getPokemon(arguments!!.getInt(helper.POKEMON_ID), arguments!!.getInt(helper.POKEMON_FORM_ID))
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        pokemonDetailedViewModel.pokemonDetailed.observe(this, Observer { pokemon ->
            pokemon?.let {
                pokemon_id_row.setup("ID", pokemon.pokemon_id.toString(), null, false)
                pokemon_name_row.setup("Name", pokemon.pokemon_name.toString(), null, false)
                pokemon_form_row.setup("Form", pokemon.FORMS_LIST!![0], null, false)
                val types = pokemon.TYPES_LIST!!
                if(types.size == 2) {
                    pokemon_types_row.setup("Types", types[0], types[1], true)
                } else {
                    pokemon_types_row.setup("Type", types[0], null, true)
                }
                pokemon_base_attack_row.setup("Base Attack Damage", it.base_attack.toString(), null, false)
                pokemon_base_defense_row.setup("Base Defense", pokemon.base_defense.toString(), null, false)
                pokemon_base_stamina_row.setup("Base Stamina", pokemon.base_stamina.toString(), null, false)
                pokemon_max_cp_row.setup("Max CP", pokemon.max_cp.toString(), null, false)
                if(!pokemon.candy_to_evolve.isNullOrBlank()) {
                    pokemon_candy_to_evolve.setup("Candy Required to Evolve", pokemon.candy_to_evolve.toString(), null, false)
                } else {
                    pokemon_candy_to_evolve.setup("Candy Required to Evolve", "Final Evolution", null, false)
                }
                if(!pokemon.buddy_distances.isNullOrBlank()) {
                    val str = pokemon.buddy_distances.toString() + " KM"
                    pokemon_buddy_distance.setup("Distance Required to get Candy", str, null, false)
                } else {
                    pokemon_buddy_distance.setup("Distance Required to get Candy", "N/A", null, false)
                }
                if(pokemon.raidExclusive == 1) {
                    pokemon_raid_exclusive.setup("Raid Exclusive Pokemon", "True", null, false)
                } else {
                    pokemon_raid_exclusive.setup("Raid Exclusive Pokemon", "False", null, false)
                }
                if(pokemon.raidLevel != 0 && pokemon.raidLevel != null) {
                    pokemon_raid_level.setup("Raid Level", pokemon.raidLevel.toString(), null, false)
                } else {
                    pokemon_raid_level.setup("Raid Level", "N/A", null, false)
                }
            }
        })
    }
}
