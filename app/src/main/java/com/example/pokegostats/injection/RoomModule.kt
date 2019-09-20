package com.example.pokegostats.injection

import android.app.Application
import com.example.pokegostats.room.PokemonGoStatsRepository
import com.example.pokegostats.room.PokemonGoStatsRoomDatabase
import com.example.pokegostats.room.dao.DateCacheDao
import com.example.pokegostats.room.dao.PokemonDao
import com.example.pokegostats.room.dao.PokemonMovesDao
import com.example.pokegostats.service.PokemonGoApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(app: Application): PokemonGoStatsRoomDatabase {
        return PokemonGoStatsRoomDatabase.getDatabase(app)
    }

    @Singleton
    @Provides
    fun providesPokemonDao(database: PokemonGoStatsRoomDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Singleton
    @Provides
    fun providesPokemonMovesDao(database: PokemonGoStatsRoomDatabase): PokemonMovesDao {
        return database.pokemonMovesDao()
    }

    @Singleton
    @Provides
    fun providesDateCacheDao(database: PokemonGoStatsRoomDatabase): DateCacheDao {
        return database.dateCacheDao()
    }

    @Singleton
    @Provides
    fun productPokemonGoStatsRepository(pokemonDao: PokemonDao,
                                        pokemonMovesDao:  PokemonMovesDao,
                                        dateCacheDao: DateCacheDao,
                                        pokemonGoApiService: PokemonGoApiService): PokemonGoStatsRepository {
        return PokemonGoStatsRepository(pokemonDao, pokemonMovesDao, dateCacheDao, pokemonGoApiService)
    }
}