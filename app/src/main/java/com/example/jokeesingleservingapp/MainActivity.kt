package com.example.jokeesingleservingapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.jokeesingleservingapp.ui.theme.JokeeSingleServingAPPTheme
import com.example.jokeesingleservingapp.ui.theme.MainScreen

class MainActivity : ComponentActivity() {

    private val viewModel: JokesContentViewModel by viewModels()

    private val sharedPreferences by lazy {
        getSharedPreferences("App_Shared_database", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokeeSingleServingAPPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(sharedPreferences, viewModel)
                }
            }
        }
    }
}