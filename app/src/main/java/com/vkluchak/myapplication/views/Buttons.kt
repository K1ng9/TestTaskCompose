package com.vkluchak.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.vkluchak.myapplication.ui.theme.White

@Composable
fun LogInButton(
    modifier: Modifier,
    buttonColor: Color = White,
    contentColor: Color = White,
    text: String,
    iconResourceId: Painter? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(size = 4.dp),
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(48.dp)
    ) {
        Row(modifier) {
            iconResourceId?.let {
                Image(
                    painter = iconResourceId,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text)
        }
    }
}