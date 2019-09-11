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
        fun newInstance(pokemonId: Int, pokemonFormName: String): PokemonDetailedListFragment = PokemonDetailedListFragment().apply {
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
                pokemonDetailedViewModel.getPokemon(arguments!!.getInt(helper.POKEMON_ID), arguments!!.getString(helper.POKEMON_FORM_NAME).toString())
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        pokemonDetailedViewModel.pokemonDetailed.observe(this, Observer { pokemon ->
            pokemon?.let {
                val pokemon = it.pokemon!!
                val form = it.pokemonForm!!
                val types = it.pokemonTypes!!
                pokemon_id_row.setup("ID", pokemon.pokemonId.toString(), null, false)
                pokemon_name_row.setup("Name", pokemon.pokemonName.toString(), null, false)
                pokemon_form_row.setup("Form", form.formName.toString(), null, false)
                if(types.size == 2) {
                    pokemon_types_row.setup("Types", types[0].type.toString(), types[1].type.toString(), true)
                } else {
                    pokemon_types_row.setup("Type", types[0].type.toString(), null, true)
                }
                pokemon_base_attack_row.setup("Base Attack Damage", pokemon.baseAttack.toString(), null, false)
                pokemon_base_defense_row.setup("Base Defense", pokemon.baseDefense.toString(), null, false)
                pokemon_base_stamina_row.setup("Base Stamina", pokemon.baseStamina.toString(), null, false)
                pokemon_max_cp_row.setup("Max CP", pokemon.maxCp.toString(), null, false)

            }
        })
    }
}
