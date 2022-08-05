package com.kyant.monet

data class ColorSpec(
    val chroma: Double,
    val hueShift: (Double) -> Double = { 0.0 }
)
