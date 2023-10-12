package com.vkluchak.myapplication.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkluchak.myapplication.R
import com.vkluchak.myapplication.ui.theme.HawkesBlue
import com.vkluchak.myapplication.ui.theme.LightSteelBlue
import com.vkluchak.myapplication.ui.theme.Lynch
import com.vkluchak.myapplication.ui.theme.MayaBlue

@Composable
fun UserAgreementField(
    modifier: Modifier,
    isUserConfirmTerms: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(modifier.padding(42.dp)) {
        Switch(
            checked = isUserConfirmTerms,
            onCheckedChange = onCheckedChange,
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
            val spannedTitle = stringResource(R.string.user_agreement_title)
            val str = stringResource(R.string.user_agreement_message, spannedTitle)
            val startIndex = str.indexOf(spannedTitle)
            val endIndex = startIndex + spannedTitle.length
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
