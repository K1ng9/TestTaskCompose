package com.vkluchak.myapplication.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkluchak.myapplication.R
import com.vkluchak.myapplication.ui.theme.Black
import com.vkluchak.myapplication.ui.theme.Lynch
import com.vkluchak.myapplication.ui.theme.Mariner
import com.vkluchak.myapplication.ui.theme.RoyalBlue
import com.vkluchak.myapplication.ui.theme.WhiteSmoke
import kotlinx.coroutines.launch

const val LOGIN_SCREEN = 0
const val REGISTRATION_SCREEN = 1

@Composable
fun LoginPage(modifier: Modifier, onPagerStateChanged: () -> Unit) {

    var isUserConfirmTerms by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = stringResource(R.string.welcome_to_minichat),
            color = Lynch,
            fontWeight = FontWeight(500),
            fontSize = 21.sp,
        )

        Spacer(modifier = Modifier.height(36.dp))

        LogInButton(
            modifier,
            buttonColor = if (isUserConfirmTerms) RoyalBlue else RoyalBlue.copy(
                alpha = 0.6f
            ),
            text = stringResource(R.string.log_in_with_facebook),
            iconResourceId = painterResource(id = R.drawable.icon_fb)
        ) { }

        Spacer(modifier = Modifier.height(8.dp))

        LogInButton(
            modifier,
            buttonColor = if (isUserConfirmTerms) Black else Black.copy(alpha = 0.6f),
            text = stringResource(R.string.continue_with_apple),
            iconResourceId = painterResource(id = R.drawable.icon_apple)
        ) {}

        Spacer(modifier = Modifier.height(8.dp))

        LogInButton(
            modifier,
            text = stringResource(R.string.join_as_guest),
            contentColor = if (isUserConfirmTerms) Mariner else Mariner.copy(alpha = 0.6f)
        ) {
            if (isUserConfirmTerms) {
                onPagerStateChanged()

            }
        }

        Spacer(modifier = Modifier.weight(1f))

        UserAgreementField(modifier, isUserConfirmTerms) {
            isUserConfirmTerms = it
        }
    }
}

@Composable
fun RegistrationPage(modifier: Modifier, onPagerStateChanged: () -> Unit) {
    BackHandler(true) {
        onPagerStateChanged()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteSmoke)
    ) {
        val focusManager = LocalFocusManager.current
        IconButton(
            modifier = modifier.then(Modifier.size(48.dp)),
            onClick = {
                focusManager.clearFocus(force = true)
                onPagerStateChanged()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu_arrow),
                contentDescription = ""
            )
        }

        InputTextField(modifier)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomScreens(modifier: Modifier, animatedKeyboardSlider: State<Dp>) {
    val pagerState = rememberPagerState(0)
    val animationScope = rememberCoroutineScope()

    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteSmoke)
            .offset(y = animatedKeyboardSlider.value),
        state = pagerState,
        pageCount = 2,
        userScrollEnabled = false,
        beyondBoundsPageCount = 1
    ) { page ->

        when (page) {
            LOGIN_SCREEN -> LoginPage(modifier) {
                animationScope.launch {
                    pagerState.animateScrollToPage(REGISTRATION_SCREEN)
                }
            }

            REGISTRATION_SCREEN -> RegistrationPage(modifier) {
                animationScope.launch {
                    pagerState.animateScrollToPage(LOGIN_SCREEN)
                }
            }
        }

    }
}

