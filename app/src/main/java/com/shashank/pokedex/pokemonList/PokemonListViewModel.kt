package com.shashank.pokedex.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.shashank.pokedex.const.Utils.Companion.PAGE_SIZE
import com.shashank.pokedex.data.models.PokedexListEntry
import com.shashank.pokedex.repository.PokeRepository
import com.shashank.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokeRepository
) :ViewModel() {

   private var CurrentPageCount=0;

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError  = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached= mutableStateOf(false)


    init {

        loadPokemonPaginated()
    }

    fun loadPokemonPaginated(){

        viewModelScope.launch {
            isLoading.value =true
            val  result  = repository.getPokemonList(PAGE_SIZE,CurrentPageCount* PAGE_SIZE)

            when(result){
                is Resource.Success -> {
                   endReached.value = CurrentPageCount* PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, result ->
                        val number = if (result.url.endsWith("/"))
                        result.url.dropLast(1).takeLastWhile { it.isDigit() }
                        else{
                            result.url.takeLastWhile {
                                it.isDigit()
                            }
                        }
                        val url = "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/${number}.svg"
                      val url2=  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(result.name.capitalize(Locale.ROOT),url, number = number.toInt())

                    }
                       CurrentPageCount++;

                    loadError.value = ""
                    isLoading.value= false;
                    pokemonList.value= pokedexEntries

                }
                is Resource.Error -> {
                 loadError.value= result.message!!
                    isLoading.value= false
                }

            }



        }
    }





    fun calcDominantColor(drawable:Drawable,onFinish:(Color) -> Unit) {
        val bmp =(drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        Palette.from(bmp).generate(){palette->
            palette?.dominantSwatch?.rgb?.let {colorValue ->
                onFinish(Color(colorValue))
            }

        }



    }
}