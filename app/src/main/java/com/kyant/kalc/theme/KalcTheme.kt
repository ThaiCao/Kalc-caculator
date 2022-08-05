package com.kyant.kalc.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.kyant.kalc.data.AppViewModel
import com.kyant.monet.Hct.Companion.toHct
import com.kyant.monet.LocalTonalPalettes
import com.kyant.monet.TonalPalettes.Companion.toTonalPalettes
import com.kyant.monet.toSrgb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AppViewModel.KalcTheme(
    content: @Composable () -> Unit
) {
    val baseColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        colorResource(id = android.R.color.system_accent1_500)
    } else Color(0xFF007FAC)
    val baseColorSrgb = baseColor.toSrgb()
    val baseColorHct = baseColorSrgb.toHct()

    var tonalPalettes by remember { mutableStateOf(baseColorSrgb.toTonalPalettes()) }
    LaunchedEffect(increment) {
        withContext(Dispatchers.IO) {
            tonalPalettes = baseColorHct.copy(h = baseColorHct.h + increment * 15).toSrgb().toTonalPalettes()
        }
    }

    MaterialTheme(typography = Fonts.googleSansTextMaterialTypography) {
        CompositionLocalProvider(
            LocalTonalPalettes provides tonalPalettes,
            LocalContentColor provides if (isSystemInDarkTheme()) Color.White else Color.Black
        ) {
            content()
        }
    }
}
