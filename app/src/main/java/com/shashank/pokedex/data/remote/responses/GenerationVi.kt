package com.shashank.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.shashank.pokedex.data.remote.model.XY

data class GenerationVi(

    @SerializedName("omegaruby-alphasapphire")
    val omegarubyAlphasapphire: OmegarubyAlphasapphire,
    @SerializedName("x-y")
    val xY: XY
)