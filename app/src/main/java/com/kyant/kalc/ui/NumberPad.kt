package com.kyant.kalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kyant.kalc.data.AppViewModel
import com.kyant.monet.a1
import com.kyant.monet.a2
import com.kyant.monet.a3
import com.kyant.monet.n1
import com.kyant.monet.withNight

@Composable
fun AppViewModel.NumberPad(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        item {
            ActionKey(
                text = "AC",
                color = 90.a3 withNight 30.a3
            ) {
                increment++
                expression = expression.copy(text = "")
            }
        }
        item {
            ParenthesisKey()
        }
        item {
            ActionKey(text = "^")
        }
        item {
            ActionKey(text = "÷")
        }

        item {
            DigitalKey(text = "7")
        }
        item {
            DigitalKey(text = "8")
        }
        item {
            DigitalKey(text = "9")
        }
        item {
            ActionKey(text = "×")
        }

        item {
            DigitalKey(text = "4")
        }
        item {
            DigitalKey(text = "5")
        }
        item {
            DigitalKey(text = "6")
        }
        item {
            ActionKey(text = "−")
        }

        item {
            DigitalKey(text = "1")
        }
        item {
            DigitalKey(text = "2")
        }
        item {
            DigitalKey(text = "3")
        }
        item {
            ActionKey(text = "+")
        }

        item {
            DigitalKey(text = "0")
        }
        item {
            DigitalKey(text = ".")
        }
        item {
            BackspaceKey()
        }
        item {
            ActionKey(
                text = "=",
                color = 90.a3 withNight 30.a3
            ) {
                increment++
                historyExpressions += expression.text
            }
        }
    }
}

@Composable
fun AppViewModel.ParenthesisKey(compact: Boolean = false) {
    val parenthesisColor = 60.n1 withNight 80.n1
    val multiplicationColor = 40.a1 withNight 80.a1
    ActionKey(
        text = "( )",
        compact = compact
    ) {
        val start = expression.annotatedString.getOrNull(expression.selection.min - 1)
        val invalidStart = start?.let { "[\\d|)]".toRegex().containsMatchIn(it.toString()) } == true

        val end = expression.annotatedString.getOrNull(expression.selection.max)
        val invalidEnd = end?.let { "[\\d|(]".toRegex().containsMatchIn(it.toString()) } == true

        expression = expression.copy(
            buildAnnotatedString {
                append(expression.annotatedString.subSequence(0, expression.selection.min))
                if (invalidStart) {
                    withStyle(SpanStyle(color = multiplicationColor)) {
                        append("×")
                    }
                }
                withStyle(SpanStyle(color = parenthesisColor)) {
                    append("()")
                }
                if (invalidEnd) {
                    withStyle(SpanStyle(color = multiplicationColor)) {
                        append("×")
                    }
                }
                append(
                    expression.annotatedString.subSequence(
                        expression.selection.max,
                        expression.annotatedString.length
                    )
                )
            },
            selection = TextRange(expression.selection.end + if (invalidStart) 2 else 1)
        )
    }
}

@Composable
fun AppViewModel.BackspaceKey() {
    ActionKey(
        imageVector = Icons.Outlined.Backspace,
        color = 90.a2 withNight 20.a2
    ) {
        if (expression.text.isNotEmpty()) {
            expression = expression.copy(
                if (expression.selection.start == expression.selection.end) {
                    buildAnnotatedString {
                        append(
                            expression.annotatedString.subSequence(
                                0,
                                (expression.selection.min - 1).coerceAtLeast(0)
                            )
                        )
                        append(
                            expression.annotatedString.subSequence(
                                expression.selection.max,
                                expression.annotatedString.length
                            )
                        )
                    }
                } else {
                    buildAnnotatedString {
                        append(expression.annotatedString.subSequence(0, expression.selection.min))
                        append(
                            expression.annotatedString.subSequence(
                                expression.selection.max,
                                expression.annotatedString.length
                            )
                        )
                    }
                },
                selection = if (expression.selection.start == expression.selection.end) {
                    TextRange((expression.selection.min - 1).coerceAtLeast(0))
                } else TextRange(expression.selection.min)
            )
        }
    }
}
