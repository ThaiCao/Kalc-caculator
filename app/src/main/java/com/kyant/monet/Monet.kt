package com.kyant.monet

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.kyant.monet.TonalPalettes.Companion.toTonalPalettes
import kotlin.math.roundToInt

val LocalTonalPalettes = staticCompositionLocalOf {
    Color(0xFF007FAC).toSrgb().toTonalPalettes()
}

inline val Int.a1
    @Composable get() = (LocalTonalPalettes.current accent1 toDouble()).toColor()

inline val Int.a2
    @Composable get() = (LocalTonalPalettes.current accent2 toDouble()).toColor()

inline val Int.a3
    @Composable get() = (LocalTonalPalettes.current accent3 toDouble()).toColor()

inline val Int.n1
    @Composable get() = (LocalTonalPalettes.current neutral1 toDouble()).toColor()

inline val Int.n2
    @Composable get() = (LocalTonalPalettes.current neutral2 toDouble()).toColor()

@Composable
infix fun Color.withNight(nightColor: Color): Color {
    return if (isSystemInDarkTheme()) nightColor else this
}

fun Srgb.toColor(): Color {
    return Color(
        (r * 255).roundToInt(),
        (g * 255).roundToInt(),
        (b * 255).roundToInt()
    )
}

fun Color.toSrgb(): Srgb {
    return Srgb(red.toDouble(), green.toDouble(), blue.toDouble())
}
