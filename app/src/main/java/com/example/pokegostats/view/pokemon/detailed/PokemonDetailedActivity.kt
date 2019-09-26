package com.example.pokegostats.view.pokemon.detailed

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.pokegostats.R
import com.example.pokegostats.service.PokemonHelper
import com.example.pokegostats.view.pokemon.detailed.adapter.PokemonDetailedPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.pokemon_detailed_activity.*

class PokemonDetailedActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonDetailedPagerAdapter
    private val helper: PokemonHelper = PokemonHelper.instance
    private lateinit var viewModel: PokemonDetailedActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_detailed_activity)

        // Creates the View Model
        val factory = PokemonDetailedActivityViewModel.Companion.Factory(this.application)
        viewModel = ViewModelProvider(this, factory).get(PokemonDetailedActivityViewModel::class.java)

        // Set Pokemon Image
        val pokemonId = intent.extras!!.getString(helper.POKEMON_ID)!!
        val pokemonName = intent.extras!!.getString(helper.POKEMON_NAME)!!
        val formId = intent.extras!!.getString(helper.POKEMON_FORM_ID)!!.toInt()
        val formName = intent.extras!!.getString(helper.POKEMON_FORM_NAME)!!
        setPokemonImage(pokemonId, pokemonName, formName)

        // Set Types
        val type1 = intent.extras!!.getString(helper.POKEMON_TYPE1)!!
        val type2: String
        if (intent.extras!!.containsKey(helper.POKEMON_TYPE2)) {
            type2 = intent.extras!!.getString(helper.POKEMON_TYPE2)!!
            helper.setPokemonTypeLook(this, tv_detailed_type1, type1)
            helper.setPokemonTypeLook(this, tv_detailed_type2, type2)
        } else {
            helper.setPokemonTypeLook(this, tv_detailed_type2, type1)
        }

        val vpPager = findViewById<ViewPager>(R.id.view_pager_detailed)
        adapter = PokemonDetailedPagerAdapter(supportFragmentManager)
        adapter.setPokemonFormId(formId)
        adapter.setPokemonId(pokemonId.toInt())
        vpPager.adapter = adapter

        val tabs = findViewById<View>(R.id.tab_layout_detailed) as TabLayout
        tabs.setupWithViewPager(vpPager)
    }

    private fun setPokemonImage(pokemonId: String, pokemonName: String, formName: String) {
        val identifier = if(formName == "alola") {
            "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
        } else {
            "pokemon_" + pokemonId + "_" + pokemonName
        }
        // Explanation for ID retrieval found here: http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
        try {
            val res = R.drawable::class.java
            val field = res.getField(identifier)
            val drawableId = field.getInt(null)
            pokemon_image.setImageResource(drawableId)
        } catch (e: Exception) {
            Log.i("DrawableWarning", "Image for $identifier doesn't exist")
            pokemon_image.setImageResource(R.color.pokemonGoAppBackground)
        }
    }
}