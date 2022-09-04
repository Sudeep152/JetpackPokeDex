package com.shashank.pokedex.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScree(){
    Surface(modifier = Modifier
        .fillMaxSize(), color = Color.DarkGray
    ) {

        Column() {
            Text(text = "What are you looking for?", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.h5)
        }


    }

}

@Preview(showBackground = true)
@Composable
fun ScreenShow(){
    MainScree()

}