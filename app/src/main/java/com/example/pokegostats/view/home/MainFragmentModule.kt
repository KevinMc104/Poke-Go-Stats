package com.example.pokegostats.view.home

import dagger.Binds
import dagger.Module

@Module
abstract class MainFragmentModule {
    @Binds
    abstract fun provideMainFragment(mainFragment: MainFragment)
}