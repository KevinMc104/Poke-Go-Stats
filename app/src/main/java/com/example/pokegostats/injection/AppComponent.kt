package com.example.pokegostats.injection

import com.example.pokegostats.PokeGoStats
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        ModuleBuilder::class
    ]
)

@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PokeGoStats): Builder

        fun build(): AppComponent
    }

    fun inject(pokeGoStats: PokeGoStats)
}