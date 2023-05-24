package com.example.jokeesingleservingapp

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeesingleservingapp.mock.jokesMock
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

sealed class ResultData {
    object Loading : ResultData()
    data class Success(val data: JokesContent) : ResultData()
    data class Error(val errorMsg: String) : ResultData()
}

class JokesContentViewModel : ViewModel() {

    companion object {
        private const val LIKED_JOKE = "LIKED_JOKE"
    }

    private val savedList = mutableListOf<JokesContent>()

    private val jokes = MutableStateFlow(generateAnotherJoke())

    val jokesContent =
        jokes.map {
            // loading from api
            delay(100)
            ResultData.Success(it) as ResultData
        }
            .catch { caused ->
                emit(ResultData.Error("Co loi ne: $caused"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ResultData.Loading
            )

    fun likeFunnyJokes(
        sharedPreferences: SharedPreferences
    ) = viewModelScope.launch {
        savedList.add(jokes.value)
        with(sharedPreferences.edit()) {
            putString(LIKED_JOKE, Gson().toJson(savedList))
            apply()
        }
        jokes.emit(generateAnotherJoke())
    }

    fun dislikeFunnyJokes() = viewModelScope.launch {
        jokes.emit(generateAnotherJoke())
    }

    private fun generateAnotherJoke(): JokesContent {
        return if (jokesMock.size > 0) {
            val randomIndex = (jokesMock.indices).random()
            val funnyData = jokesMock[randomIndex]
            jokesMock.removeAt(randomIndex)
            funnyData
        } else {
            JokesContent("There is no...", "That's all the jokes for today! Come back another day!")
        }
    }

}