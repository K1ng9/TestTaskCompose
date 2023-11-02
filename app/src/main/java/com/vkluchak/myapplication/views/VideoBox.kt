package com.vkluchak.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vkluchak.myapplication.MainViewModel
import com.vkluchak.myapplication.R


@Composable
fun VideoBox(viewModel: MainViewModel, animatedKeyboardSlider: State<Dp>) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        VideoPlayer(viewModel.player)

        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )

        UserCounterText(viewModel, animatedKeyboardSlider)
    }
}
