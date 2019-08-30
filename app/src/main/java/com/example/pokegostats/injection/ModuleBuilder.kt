package com.example.pokegostats.injection

import com.example.pokegostats.view.home.MainFragment
import com.example.pokegostats.view.home.MainFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragmentInject(): MainFragment
}