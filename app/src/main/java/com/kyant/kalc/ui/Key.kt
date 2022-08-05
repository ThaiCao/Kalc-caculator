package com.kyant.kalc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kyant.kalc.data.AppViewModel
import com.kyant.kalc.theme.Fonts
import com.kyant.kalc.utils.tickVibrate
import com.kyant.monet.a1
import com.kyant.monet.a2
import com.kyant.monet.n1
import com.kyant.monet.withNight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppViewModel.DigitalKey(
    text: String,
    color: Color = 98.n1 withNight 16.n1,
    onClick: () -> Unit = {
        expression = expression.copy(
            buildAnnotatedString {
                append(expression.annotatedString.subSequence(0, expression.selection.min))
                append(text)
                append(
                    expression.annotatedString.subSequence(
                        expression.selection.max,
                        expression.annotatedString.length
                    )
                )
            },
            selection = TextRange(expression.selection.end + 1)
        )
    }
) {
    val context = LocalContext.current
    Surface(
        onClick = {
            onClick()
            context.tickVibrate()
        },
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        shape = CircleShape,
        color = color
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontFamily = Fonts.googleSansFontFamily,
                maxLines = 1,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppViewModel.ActionKey(
    text: String,
    color: Color = 90.a1 withNight 25.a2,
    textColor: Color = 40.a1 withNight 80.a1,
    compact: Boolean = false,
    onClick: () -> Unit = {
        expression = expression.copy(
            buildAnnotatedString {
                append(expression.annotatedString.subSequence(0, expression.selection.min))
                withStyle(SpanStyle(color = textColor)) {
                    append(text)
                }
                append(
                    expression.annotatedString.subSequence(
                        expression.selection.max,
                        expression.annotatedString.length
                    )
                )
            },
            selection = TextRange(expression.selection.end + 1)
        )
    }
) {
    val context = LocalContext.current
    Surface(
        onClick = {
            onClick()
            context.tickVibrate()
        },
        modifier = if (!compact) {
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        } else Modifier,
        shape = CircleShape,
        color = color
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = if (!compact) 0.dp else 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontFamily = Fonts.googleSansFontFamily,
                maxLines = 1,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionKey(
    imageVector: ImageVector,
    color: Color = 90.a1 withNight 30.a2,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Surface(
        onClick = {
            onClick()
            context.tickVibrate()
        },
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        shape = CircleShape,
        color = color
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
