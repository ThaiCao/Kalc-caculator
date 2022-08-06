package com.kyant.kalc.ui

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kyant.kalc.data.AppViewModel
import com.kyant.monet.n1
import com.kyant.monet.withNight

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppViewModel.Main() {
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as Activity)
    Surface(color = 95.n1 withNight 10.n1) {
        AnimatedContent(targetState = historyPanelExpanded) {
            if (!it) {
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ExpressionDisplay(modifier = Modifier.weight(1f))
                        NumberPad()
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ExpressionDisplay(
                                modifier = Modifier.weight(1f),
                                compact = true
                            )
                            SplitNumberPadStart(modifier = Modifier.navigationBarsPadding())
                        }
                        SplitNumberPadEnd(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            } else {
                History()
            }
        }
    }
}
