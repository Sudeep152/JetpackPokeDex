package com.shashank.pokedex.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shashank.pokedex.R
import com.shashank.pokedex.ui.theme.myFontTyep


@Composable
fun PokemonListScreen(
    navController: NavController
){
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF424242)) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Icon(
                    imageVector = Icons.Default.Settings, contentDescription = null,
                    tint = Color.White, modifier = Modifier
                        .size(90.dp)
                        .padding(20.dp)
                )
            }


            Text(text = "What are you looking fro?",
                style = MaterialTheme.typography.h3,
                color = Color.White,
            modifier = Modifier.padding(20.dp), fontFamily  = myFontTyep
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            SearchBar(hint = "Search Pokemon...", modifier = Modifier.padding(10.dp))

        }



       }



}


@Composable
fun SearchBar(modifier: Modifier=Modifier,hint :String=""
,onSearch:(String) -> Unit ={}
){
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color(0xff242124), CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it != FocusState.Active && text.isEmpty()
                }
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ShowScreen(){
    PokemonListScreen(navController = rememberNavController())

}

