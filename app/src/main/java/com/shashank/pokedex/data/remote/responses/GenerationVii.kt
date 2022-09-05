package com.shashank.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.shashank.pokedex.data.remote.model.Icons

data class GenerationVii(
    val icons: Icons,
    @SerializedName("ultra-sun-ultra-moon")
    val ultrasunultramoon: UltraSunUltraMoon
)