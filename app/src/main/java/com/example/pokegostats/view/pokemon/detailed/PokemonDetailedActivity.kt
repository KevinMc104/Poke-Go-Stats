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
        // Checking specific forms because the database doesn't have pics for all forms
        // Can remove these checks when all form images are added
        var identifier = when(formName) {
            "alola" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Mew Two Armored Form is labeled as 'a' from RapidAPI
            "a" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Castform Forms
            "rainy" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "sunny" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "snowy" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Cherrim Forms
            "overcast" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Deoxys Forms
            "attack" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "defense" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "speed" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Burmy and Wormadam Forms
            "plant" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "sandy" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "trash" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Shellos and Gastrodon Forms
            "west_sea" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "east_sea" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Rotom Forms
            "fan" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "frost" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "heat" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "mow" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "wash" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Giratina forms
            "altered" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "origin" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Shaymin Forms
            "land" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "sky" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            // Arceus Forms
            "bug" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "dark" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "dragon" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "electric" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "fairy" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "fighting" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "fire" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "flying" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "ghost" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "grass" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "ground" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "ice" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "poison" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "psychic" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "rock" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "steel" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            "water" -> "pokemon_" + pokemonId + "_" + pokemonName + "_" + formName
            else -> "pokemon_" + pokemonId + "_" + pokemonName
        }
        // Files can't have any special characters besides "_"
        // Remove any non alphanumeric characters from the search
        val re = Regex("[^A-Za-z0-9_]")
        identifier = re.replace(identifier, "")
        // Explanation for ID retrieval found here: http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
        // getIdentifier is the slower way to do this
        try {
            val res = R.drawable::class.java
            val drawableId = res.getField(identifier).getInt(null)
            pokemon_image.setImageResource(drawableId)
        } catch (e: Exception) {
            Log.i("DrawableWarning", "Image for $identifier doesn't exist")
        }
    }
}