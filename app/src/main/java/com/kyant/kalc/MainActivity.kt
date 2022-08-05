package com.kyant.kalc

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.kyant.kalc.data.AppViewModel
import com.kyant.kalc.theme.KalcTheme
import com.kyant.kalc.ui.Main

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContent {
            with(viewModel) {
                KalcTheme {
                    Main()
                }
                BackHandler(enabled = historyPanelExpanded) {
                    historyPanelExpanded = false
                }
            }
        }
    }
}
