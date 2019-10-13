package com.analytics.pokegostats.injection

import android.app.Application
import android.content.Context
import com.analytics.pokegostats.PokeGoStats
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