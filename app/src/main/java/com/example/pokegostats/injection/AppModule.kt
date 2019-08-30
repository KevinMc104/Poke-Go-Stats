package com.example.pokegostats.injection

import android.app.Application
import android.content.Context
import com.example.pokegostats.PokeGoStats
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun provideApp(pokeGoStats: PokeGoStats): Application

    @Singleton
    @Binds
    abstract fun provideContext(pokeGoStats: PokeGoStats): Context
}