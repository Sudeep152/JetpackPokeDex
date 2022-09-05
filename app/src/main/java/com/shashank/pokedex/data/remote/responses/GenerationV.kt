package com.shashank.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("balck-white")
    val blackwhite: BlackWhite
)