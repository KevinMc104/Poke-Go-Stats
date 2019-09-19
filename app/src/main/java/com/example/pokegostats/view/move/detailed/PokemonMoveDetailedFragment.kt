package com.example.pokegostats.view.move.detailed

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
import kotlinx.android.synthetic.main.pokemon_moves_detailed_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class PokemonMoveDetailedFragment : Fragment() {

    // Reference to the RecyclerView adapter
    private lateinit var viewModel: PokemonMoveDetailedFragmentViewModel
    private val helper: PokemonHelper = PokemonHelper.instance

    /**
     * TODO: Inject this in the repository instead of fragment
     */
    @Inject
    protected lateinit var service: PokemonGoApiService

    companion object {
        fun newInstance(moveName: String): PokemonMoveDetailedFragment = PokemonMoveDetailedFragment().apply {
            val args = Bundle()
            args.putString(helper.POKEMON_MOVE_NAME, moveName)
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
        val factory = PokemonMoveDetailedFragmentViewModel.Companion.Factory(requireActivity().application, service)
        viewModel = ViewModelProvider(this, factory).get(PokemonMoveDetailedFragmentViewModel::class.java)

        return inflater.inflate(R.layout.pokemon_moves_detailed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch (Dispatchers.Main) {
            // call out to Repository to get stats
            try {
                viewModel.getMove(arguments!!.getString(helper.POKEMON_MOVE_NAME).toString())
            } catch (e: IOException) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), "network failure :(", Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Snackbar.make(activity!!.findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.pokemonMoveDetailed.observe(this, Observer { move ->
            move?.let {
                move_name.setup("Move", it.name, null, false)
                move_power.setup("Power", it.power.toString(), null, false)
                move_type.setup("Type", it.typeName, null, true)
                if(it.criticalChance == 0.0) {
                    move_crit_chance.setup("Crit Chance", "0%", null, false)
                } else {
                    move_crit_chance.setup("Crit Chance", it.criticalChance.toString() + "%", null, false)
                }
                move_duration.setup("Duration", it.duration.toString(), null, false)
                move_stamina_loss_scaler.setup("Stamina Loss Scaler", it.staminaLossScaler, null, false)
                move_energy_delta.setup("Energy Delta", it.energyDelta.toString(), null, false)
            }
        })
    }
}