package com.kyant.kalc.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.VibratorManager

fun Context.tickVibrate() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
            .vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
    }
}
