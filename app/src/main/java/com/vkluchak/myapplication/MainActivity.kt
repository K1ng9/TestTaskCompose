package com.vkluchak.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.vkluchak.myapplication.ui.theme.TestTaskComposeTheme
import com.vkluchak.myapplication.views.BottomScreens
import com.vkluchak.myapplication.views.VideoBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TestTaskComposeTheme {
                val viewModel = hiltViewModel<MainViewModel>()

                MainView(viewModel)
            }
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        val keyboardHeightDp = WindowInsets.ime.getBottom(LocalDensity.current)
        val animatedKeyboardSlider =
            animateDpAsState(targetValue = -(keyboardHeightDp.dp / 6.5f), label = "")

        VideoBox(viewModel, animatedKeyboardSlider)

        BottomScreens(modifier, animatedKeyboardSlider)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTaskComposeTheme {
        val viewModel = hiltViewModel<MainViewModel>()
        MainView(viewModel)
    }
}
