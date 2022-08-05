package com.kyant.kalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.kalc.data.AppViewModel
import com.kyant.monet.a3
import com.kyant.monet.withNight

@Composable
fun AppViewModel.SplitNumberPadStart(modifier: Modifier = Modifier) {
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
                color = 90.a3 withNight 30.a3,
                compact = true
            ) {
                increment++
                expression = expression.copy(text = "")
            }
        }
        item {
            ParenthesisKey(compact = true)
        }
        item {
            ActionKey(text = "%", compact = true)
        }
        item {
            ActionKey(
                text = "=",
                color = 90.a3 withNight 30.a3,
                compact = true
            ) {
                increment++
                historyExpressions += expression.text
            }
        }
        items(listOf("÷", "×", "−", "+")) {
            ActionKey(text = it, compact = true)
        }
    }
}
