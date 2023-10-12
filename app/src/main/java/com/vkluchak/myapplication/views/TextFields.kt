package com.vkluchak.myapplication.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vkluchak.myapplication.MainViewModel
import com.vkluchak.myapplication.R
import com.vkluchak.myapplication.model.Digit
import com.vkluchak.myapplication.model.compareTo
import com.vkluchak.myapplication.ui.theme.Lynch
import com.vkluchak.myapplication.ui.theme.RoyalBlue
import com.vkluchak.myapplication.ui.theme.White
import com.vkluchak.myapplication.ui.theme.WhiteSmoke



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UserCounterText(viewModel: MainViewModel, animatedVisibility: State<Dp>) {
    val userCounter by viewModel.userCounter.collectAsState()

    Row(
        modifier = Modifier
            .offset(y = animatedVisibility.value)
            .animateContentSize()
            .padding(6.dp),
    ) {
        userCounter.toString()
            .mapIndexed { index, c -> Digit(c, userCounter, index) }
            .forEach { digit ->
                AnimatedContent(
                    targetState = digit,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } with slideOutVertically { it }
                        } else {
                            slideInVertically { it } with slideOutVertically { -it }
                        }
                    }, label = ""
                ) { digit ->
                    Text(
                        "${digit.digitChar}",
                        textAlign = TextAlign.Center,
                        color = White
                    )
                }
            }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.users_online),
            color = White
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(modifier: Modifier) {
    var text by remember { mutableStateOf("") }
    Column {
        Text(
            text = stringResource(R.string.first_name_nickname),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Lynch
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Lynch,
                containerColor = WhiteSmoke,
                focusedIndicatorColor = White,
                unfocusedIndicatorColor = White,
                cursorColor = RoyalBlue
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
          )
    }
}