package com.shashank.pokedex.di

import com.shashank.pokedex.const.Utils.Companion.BASE_URL
import com.shashank.pokedex.data.remote.PokeApi
import com.shashank.pokedex.repository.PokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideRepository(api: PokeApi):PokeRepository{
        return PokeRepository(api)
    }

    @Singleton
    @Provides
    fun  providePokeApi():PokeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }



}