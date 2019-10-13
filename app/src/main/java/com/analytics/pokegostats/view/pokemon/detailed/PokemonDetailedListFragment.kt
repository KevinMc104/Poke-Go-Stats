package com.analytics.pokegostats.view.pokemon.detailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.analytics.pokegostats.R
import com.analytics.pokegostats.room.PokemonGoStatsRepository
import com.analytics.pokegostats.service.PokemonHelper
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
    private lateinit var viewModel: PokemonDetailedListFragmentViewModel
    private val helper: PokemonHelper = PokemonHelper.instance
    @Inject lateinit var repository: PokemonGoStatsRepository

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

        // Creates the View Model
        val factory = PokemonDetailedListFragmentViewModel.Companion.Factory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory).get(PokemonDetailedListFragmentViewModel::class.java)

        return inflater.inflate(R.layout.pokemon_detailed_stats_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main) {
            // call out to Repository to get stats
            try {
                viewModel.getPokemon(arguments!!.getInt(helper.POKEMON_ID), arguments!!.getInt(helper.POKEMON_FORM_ID))
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.pokemonDetailed.observe(this, Observer { pokemon ->
            pokemon?.let {
                pokemon_id_row.setup("ID", pokemon.pokemon_id.toString(), null, false)
                pokemon_name_row.setup("Name", pokemon.pokemon_name.toString(), null, false)
                pokemon_form_row.setup("Form", pokemon.FORMS_LIST!![0], null, false)
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
                if(pokemon.raid_exclusive == 1) {
                    pokemon_raid_exclusive.setup("Raid Exclusive Pokemon", "True", null, false)
                } else {
                    pokemon_raid_exclusive.setup("Raid Exclusive Pokemon", "False", null, false)
                }
                if(pokemon.raid_level != 0 && pokemon.raid_level != null) {
                    pokemon_raid_level.setup("Raid Level", pokemon.raid_level.toString(), null, false)
                } else {
                    pokemon_raid_level.setup("Raid Level", "N/A", null, false)
                }
                if(pokemon.nested_pokemon == 1) {
                    pokemon_nested_pokemon.setup("Can be Nested", "True", null, false)
                } else {
                    pokemon_nested_pokemon.setup("Can be Nested", "False", null, false)
                }
                if(pokemon.shiny_found_egg == 1) {
                    pokemon_shiny_found_egg.setup("Shiny can be found in Egg", "True", null, false)
                } else {
                    pokemon_shiny_found_egg.setup("Shiny can be found in Egg", "False", null, false)
                }
                if(pokemon.shiny_found_evolution == 1) {
                    pokemon_shiny_found_evolution.setup("Shiny can be found in Evolution", "True", null, false)
                } else {
                    pokemon_shiny_found_evolution.setup("Shiny can be found in Evolution", "False", null, false)
                }
                if(pokemon.shiny_found_raid == 1) {
                    pokemon_shiny_found_raid.setup("Shiny can be found in Raid", "True", null, false)
                } else {
                    pokemon_shiny_found_raid.setup("Shiny can be found in Raid", "False", null, false)
                }
                if(pokemon.shiny_found_wild == 1) {
                    pokemon_shiny_found_wild.setup("Shiny can be found in Wild", "True", null, false)
                } else {
                    pokemon_shiny_found_wild.setup("Shiny can be found in Wild", "False", null, false)
                }
                if(pokemon.released_pokemon == 1) {
                    pokemon_released_pokemon.setup("Released to Pokemon Go", "True", null, false)
                } else {
                    pokemon_released_pokemon.setup("Released to Pokemon Go", "False", null, false)
                }
                if(pokemon.possible_ditto == 1) {
                    pokemon_possible_ditto.setup("Can be Ditto", "True", null, false)
                } else {
                    pokemon_possible_ditto.setup("Can be Ditto", "False", null, false)
                }
                val attackProb = pokemon.FORMS_LIST!![1] + "%"
                pokemon_attack_probability.setup("Attack Chance", attackProb, null, false)

                val baseCaptureRate = pokemon.FORMS_LIST!![2] + "%"
                pokemon_base_capture_rate.setup("Base Capture Rate", baseCaptureRate, null, false)

                val baseFleeRate = pokemon.FORMS_LIST!![3] + "%"
                pokemon_base_flee_rate.setup("Base Flee Chance", baseFleeRate, null, false)

                val dodgeProbability = pokemon.FORMS_LIST!![4] + "%"
                pokemon_dodge_probability.setup("Dodge Chance", dodgeProbability, null, false)

                val minPokemonActionFrequency = pokemon.FORMS_LIST!![5]
                val maxPokemonActionFrequency = pokemon.FORMS_LIST!![6]
                if(maxPokemonActionFrequency == "0.0") {
                    pokemon_action_frequency.setup("Action Frequency", "Doesn't Attack", null, false)
                } else {
                    pokemon_action_frequency.setup("Action Frequency", "$minPokemonActionFrequency - $maxPokemonActionFrequency Seconds", null, false)
                }
            }
        })
    }
}