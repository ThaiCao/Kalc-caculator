package com.kyant.kalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kyant.kalc.data.AppViewModel
import com.kyant.kalc.math.Evaluator
import com.kyant.kalc.theme.Fonts
import com.kyant.monet.n1
import com.kyant.monet.withNight

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppViewModel.ExpressionDisplay(
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = rememberScrollState()
    LaunchedEffect(expression.text) {
        state.animateScrollTo(state.maxValue)
    }
    LaunchedEffect(expression.selection) {
        keyboardController?.hide()
    }
    val focusRequester = remember { FocusRequester() }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .systemGestureExclusion(),
        shape = if (!compact) RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp)
        else RoundedCornerShape(0.dp, 0.dp, 32.dp, 0.dp),
        color = 98.n1 withNight 20.n1
    ) {
        Box(modifier = if (!compact) Modifier.statusBarsPadding() else Modifier) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .horizontalScroll(state)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = expression,
                    onValueChange = { expression = it },
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .focusRequester(focusRequester),
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        color = LocalContentColor.current,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.googleSansFontFamily
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    cursorBrush = SolidColor(LocalContentColor.current)
                )
            }
            Text(
                text = Evaluator.eval(expression.text)?.toEngineeringString()
                    ?: "Invalid input".takeIf { expression.text.isNotEmpty() }
                    ?: "",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                color = 40.n1 withNight 80.n1,
                fontWeight = FontWeight.Medium,
                fontFamily = Fonts.googleSansFontFamily,
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium
            )
            Icon(
                imageVector = Icons.Outlined.History,
                contentDescription = null,
                modifier = Modifier
                    .align(if (!compact) Alignment.TopEnd else Alignment.TopStart)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(100.n1 withNight 25.n1)
                    .clickable { historyPanelExpanded = true }
                    .padding(16.dp)
                    .size(24.dp)
            )
        }
    }
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {}
    }
}
