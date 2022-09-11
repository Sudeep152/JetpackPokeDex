package com.shashank.pokedex.pokemonList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.decode.Decoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import com.shashank.pokedex.data.models.PokedexListEntry
import com.shashank.pokedex.data.remote.responses.PokemonList
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
                        .size(10.dp)
                        .padding(20.dp)
                )
            }


            Text(text = "What are you looking for?",
                style = MaterialTheme.typography.h3,
                color = Color.White,
            modifier = Modifier.padding(20.dp), fontFamily  = myFontTyep
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            SearchBar(hint = "Search Pokemon...", modifier = Modifier.padding(10.dp))

            Spacer(modifier = Modifier.heightIn(16.dp))
            com.shashank.pokedex.pokemonList.PokemonList(navController = navController)
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

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltNavGraphViewModel(),
){

    val DefaultColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(DefaultColor)
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(listOf(
                    dominantColor, DefaultColor
                ))
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )

            }
    ){
        Column {
            CoilImage(data = ImageRequest.Builder(LocalContext.current)
                . decoder(SvgDecoder(LocalContext.current))
                .data(entry.imageUrl).target{
                    viewModel.calcDominantColor(it){color ->
                        dominantColor =color
                    }
                }
                .build(),
            contentDescription = entry.pokemonName, fadeIn = true,
            modifier = Modifier
                .size(120.dp)
                .align(CenterHorizontally)) {

                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.5f)
                )
            }
            Spacer(modifier = Modifier.heightIn(20.dp))
            Text(text = entry.pokemonName,
            fontFamily = myFontTyep
                , color = Color.White
                , style = MaterialTheme.typography.h4
                , fontSize = 30.sp
                , textAlign =TextAlign.Center

            , modifier = Modifier.fillMaxWidth())
        }
    }


}


@Composable
fun PokedexRow(
    rowIndex:Int,entries :List<PokedexListEntry>,
    navController: NavController
){


    Column {
       PokedexEntry(entry = entries[rowIndex], navController =navController 
       , modifier = Modifier
               .heightIn(100.dp)
               .widthIn(100.dp))
     Spacer(modifier = Modifier.heightIn(10.dp))
    }
    


}



@Composable
fun PokemonList(
    navController: NavController,
     viewModel: PokemonListViewModel = hiltNavGraphViewModel()
){

    val pokemonList by remember {
        viewModel.pokemonList
    }
    val endReached by remember {
        viewModel.endReached
    }
    val loadError by remember {viewModel.loadError
    }
    val isLoading by remember {
        viewModel.isLoading
    }


    LazyColumn(
        contentPadding = PaddingValues(16.dp)


    ){
              
        items(pokemonList.size){
            
            if (it >= pokemonList.size-1 && !endReached){
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries =pokemonList , navController =navController )
        }
    }






}


@Preview(showBackground = true)
@Composable
fun ShowScreen(){
    PokemonListScreen(navController = rememberNavController())

}

