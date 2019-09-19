package com.example.pokegostats.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.pokegostats.R
import com.example.pokegostats.view.home.adapter.PokemonPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonPagerAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Creates the View Model
        val factory = MainViewModel.Companion.Factory(this.application)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        val vpPager = findViewById<ViewPager>(R.id.view_pager_main)
        adapter = PokemonPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapter

        val tabs = findViewById<View>(R.id.tab_layout_main) as TabLayout
        tabs.setupWithViewPager(vpPager)
    }
}
