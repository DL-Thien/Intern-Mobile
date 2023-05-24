package com.example.jokeesingleservingapp.ui.theme

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jokeesingleservingapp.JokesContent
import com.example.jokeesingleservingapp.JokesContentViewModel
import com.example.jokeesingleservingapp.R
import com.example.jokeesingleservingapp.ResultData

@Composable
fun MainScreen(
    sharedPreferences: SharedPreferences,
    viewModel: JokesContentViewModel,
    modifier: Modifier = Modifier,
) {
    val jokesState = viewModel.jokesContent.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        TopBar(
            name = "Jim HLS", description = "Handicrafted by",
            image = painterResource(id = R.drawable.hl_solution)
        )
        DescriptionTheJoke()
        ContentTheJoke(jokesState)
        Spacer(modifier = modifier.height(20.dp))
        ButtonSection(viewModel, sharedPreferences = sharedPreferences)
        Spacer(modifier = modifier.height(20.dp))
        Box(
            modifier = modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        Description()
    }
}

@Composable
fun TopBar(
    name: String,
    description: String,
    image: Painter,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 20.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(text = description, fontSize = 12.sp, color = Color.Gray)
                Text(text = name, fontSize = 12.sp)
            }
            Spacer(modifier = modifier.width(6.dp))
            RoundImage(image = painterResource(id = R.drawable.sun_flower))
        }

    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier
            .size(40.dp)
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
            .clip(CircleShape),
        contentScale = ContentScale.Inside
    )
}

@Composable
fun DescriptionTheJoke() {
    val colorBoxDes = Color(0xFF29b363)
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorBoxDes)
            .padding(20.dp)
            .height(60.dp)
    ) {
        Text(
            text = "A joke a day keeps the doctor away",
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1
        )
        Text(
            text = "If you joke wrong way, your teeth have to pay. (Serious)",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1
        )
    }
}

@Composable
fun ContentTheJoke(
    jokesState: State<ResultData>,
    modifier: Modifier = Modifier
) {

    val joke = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = jokesState.value) {
        val state = jokesState.value
        when (state) {
            is ResultData.Success -> {
                joke.value = state.data.text
            }

            is ResultData.Error -> {
                // show dialog
            }

            else -> {}
        }
    }

    Box(

        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(360.dp)
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text(text = joke.value)
    }
}

@Composable
fun ButtonSection(
    viewModel: JokesContentViewModel,
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferences
) {
    val colorLikeButton = Color(0xFF2c7edb)
    val colorDislikeButton = Color(0xFF29b363)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        ActionButton(
            text = "This is Funny!",
            color = colorLikeButton,
            modifier = modifier.clickable {
                viewModel.likeFunnyJokes(sharedPreferences)
            })
        ActionButton(
            text = "This is not Funny.",
            color = colorDislikeButton,
            modifier = modifier.clickable {
                viewModel.dislikeFunnyJokes()
            })
    }

}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .background(color)
            .padding(12.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "This app is created as part of Hlsolutions program. " +
                    "The material con-tained on this website are provided for " +
                    "general information only and do not constitute any from of advice. " +
                    "HLS assumes no responsibility for the accuracy of any particular statement and accepts " + "" +
                    "no liability for any loss or damage which may arise from reliance on the information contained on this site.",
            style = TextStyle(color = Color.Gray, textAlign = TextAlign.Center, fontSize = 12.sp),
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(text = "Copyright 2021 HLS", fontSize = 16.sp, color = Color.DarkGray)
    }

}