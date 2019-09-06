package com.example.pokegostats.injection

import android.app.Application
import com.example.pokegostats.service.RapidPokemonGoApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    private val RAPID_POKEMON_GO_API_URL = "https://pokemon-go1.p.rapidapi.com"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
            .create()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        return Cache(File(application.cacheDir, "http-cache"), 10 * 1024 * 1024)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient().newBuilder()
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideRapidPokemonGoApiService(retrofit: Retrofit.Builder): RapidPokemonGoApiService {
        return retrofit.baseUrl(RAPID_POKEMON_GO_API_URL)
            .build()
            .create(RapidPokemonGoApiService::class.java)
    }
}
