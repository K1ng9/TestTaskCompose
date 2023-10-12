package com.vkluchak.myapplication

import android.content.Context
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.vkluchak.myapplication.ui.theme.Black
import com.vkluchak.myapplication.ui.theme.HawkesBlue
import com.vkluchak.myapplication.ui.theme.LightSteelBlue
import com.vkluchak.myapplication.ui.theme.Lynch
import com.vkluchak.myapplication.ui.theme.Mariner
import com.vkluchak.myapplication.ui.theme.MayaBlue
import com.vkluchak.myapplication.ui.theme.RoyalBlue
import com.vkluchak.myapplication.ui.theme.TestTaskComposeTheme
import com.vkluchak.myapplication.ui.theme.WhiteSmoke
import com.vkluchak.myapplication.views.InputTextField
import com.vkluchak.myapplication.views.LogInButton
import com.vkluchak.myapplication.views.UserCounterText
import com.vkluchak.myapplication.views.VideoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestTaskComposeTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                // A surface container using the 'background' color from the theme
                MainView(viewModel)
            }
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    var isUserConfirmTerms by remember { mutableStateOf(true) }

    var count by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {

        var keyboardHeightDp by remember { mutableStateOf(0f) }
        val animatedVisibility =
            animateDpAsState(targetValue = -(keyboardHeightDp.dp / 3.5f), label = "")

        Box(
            modifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            VideoPlayer(viewModel.player)

            Image(
                painter = painterResource(id = R.drawable.watermark),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = -(animatedVisibility.value / 3))
            )
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .offset(y = -(animatedVisibility.value / 3))
            )

            UserCounterText(viewModel, animatedVisibility)
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = animatedVisibility.value),
            color = WhiteSmoke
        ) {
            Row {
                var isLoginState by remember {
                    mutableStateOf(true)
                }
                BackHandler(true) {
                    if (!isLoginState) {
                        isLoginState = true
                    }
                }

                AnimatedVisibility(
                    visible = isLoginState,
                    modifier = Modifier.fillMaxWidth(),
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }
                    )
                ) {
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
                            iconResourceId = R.drawable.icon_fb,
                            onClick = { }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LogInButton(
                            modifier,
                            buttonColor = if (isUserConfirmTerms) Black else Black.copy(alpha = 0.6f),
                            text = stringResource(R.string.continue_with_apple),
                            iconResourceId = R.drawable.icon_apple,
                            onClick = {
                                if (isUserConfirmTerms) {
                                    count--
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LogInButton(
                            modifier,
                            text = stringResource(R.string.join_as_guest),
                            contentColor = if (isUserConfirmTerms) Mariner else Mariner.copy(alpha = 0.6f),
                            onClick = {
                                if (isUserConfirmTerms) {
                                    count++
                                    isLoginState = !isLoginState
                                }
                            })

                        Row(modifier.padding(42.dp)) {
                            Switch(
                                checked = isUserConfirmTerms,
                                onCheckedChange = {
                                    isUserConfirmTerms = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = LightSteelBlue,
                                    checkedTrackColor = HawkesBlue,
                                    uncheckedThumbColor = LightSteelBlue,
                                    uncheckedTrackColor = HawkesBlue,
                                    uncheckedBorderColor = LightSteelBlue
                                )
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            val annotatedLinkString = buildAnnotatedString {
                                val str = stringResource(R.string.user_agreement_message)
                                val startIndex = str.indexOf("User")
                                val endIndex = startIndex + 14
                                append(str)
                                addStyle(
                                    style = SpanStyle(
                                        color = MayaBlue,
                                        textDecoration = TextDecoration.Underline
                                    ), start = startIndex, end = endIndex
                                )
                            }
                            Text(
                                text = annotatedLinkString,
                                color = Lynch,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
                    val bottomPixels = WindowInsets.ime.getBottom(LocalDensity.current)
                    var text by remember { mutableStateOf("") }

                    LaunchedEffect(key1 = isVisible) {
                        text = if (isVisible) {
                            bottomPixels.toString()
                        } else {
                            bottomPixels.toString()
                        }
                    }

                    // calculate keyboard height
                    val rootView = LocalView.current
                    val context = LocalContext.current

                    DisposableEffect(Unit) {
                        val heightThresholdPx =
                            100 // Мінімальна висота в пікселях, яку вважати клавіатурою
                        val density = rootView.resources.displayMetrics.density

                        val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                            val rect = android.graphics.Rect()
                            rootView.getWindowVisibleDisplayFrame(rect)
                            val screenHeight = rootView.height
                            val keypadHeight = screenHeight - rect.bottom

                            if (keypadHeight > heightThresholdPx) { // клавіатура відображена
                                keyboardHeightDp = keypadHeight / density
                            } else { // клавіатура прихована
                                keyboardHeightDp = 0f
                            }
                        }

                        rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

                        onDispose {
                            rootView.viewTreeObserver.removeOnGlobalLayoutListener(
                                globalLayoutListener
                            )
                        }
                    }

                    IconButton(
                        modifier = modifier.then(Modifier.size(48.dp)),
                        onClick = {
                            hideKeyboard(context, rootView)
                            isLoginState = !isLoginState
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
        }
    }
}


fun hideKeyboard(context: Context, view: android.view.View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTaskComposeTheme {
        val viewModel = hiltViewModel<MainViewModel>()
        MainView(viewModel)
    }
}
