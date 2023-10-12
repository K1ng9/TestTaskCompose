package com.vkluchak.myapplication.views

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView


@Composable
fun VideoPlayer(player: Player) {
    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = player
                it.useController = false
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4 / 4f)
    )
}
