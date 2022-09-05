package com.shashank.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GenerationIv(
    @SerializedName("diamond-pearl")
    val diamondpearl: DiamondPearl,
    @SerializedName("heartgold-soulsilver")
    val heartgoldsoulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)