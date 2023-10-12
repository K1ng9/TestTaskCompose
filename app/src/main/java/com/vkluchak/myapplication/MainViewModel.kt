package com.vkluchak.myapplication

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class MainViewModel @Inject constructor(
    val player: Player,
    private val getLocalVideoUri: Uri
    ) : ViewModel() {

    private val _userCounter = MutableStateFlow(0)
    val userCounter: StateFlow<Int> get() = _userCounter

    init {
        player.prepare()
        player.repeatMode = Player.REPEAT_MODE_ONE
        playLocalVideo()

        // generateRandomNumbers for user counter
        generateRandomNumbers()
    }

    private fun playLocalVideo() {
        player.setMediaItem(
            MediaItem.fromUri(getLocalVideoUri)
        )
        player.play()
    }

    private fun generateRandomNumbers() {
        viewModelScope.launch {
            randomFlow().collect { number ->
                _userCounter.value = number
            }
        }
    }

    private fun randomFlow(): Flow<Int> = flow {
        while (true) {
            emit(Random.nextInt(15, 35))
            delay(1000)
        }
    }.scan(215000) { accumulator, newValue -> accumulator + newValue }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}