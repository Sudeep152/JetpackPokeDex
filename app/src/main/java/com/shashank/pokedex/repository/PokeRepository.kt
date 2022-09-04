package com.shashank.pokedex.repository

import com.shashank.pokedex.data.remote.PokeApi
import com.shashank.pokedex.data.remote.responses.Pokemon
import com.shashank.pokedex.data.remote.responses.PokemonList
import com.shashank.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokeRepository @Inject constructor( private  val api:PokeApi) {


  suspend fun  getPokemonList(limit:Int,offset:Int):Resource<PokemonList>{

    val response = try {
        api.getAllPokemon(limit,offset)
    }catch (e:Exception){
      return Resource.Error(message = "An unknown error occured.")
    }

    return  Resource.Success(response)
  }

  suspend fun getPokemonInfo(name: String):Resource<Pokemon>{

    val response  = try {
        api.getPokemon(name)
    }catch (e:Exception){
      return Resource.Error(message = "An unknown error occured.")
    }

   return Resource.Success(response)
  }




}