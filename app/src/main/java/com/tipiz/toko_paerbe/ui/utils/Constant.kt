package com.tipiz.toko_paerbe.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64

object Constant {

    //SplashScreen Fragment
    const val SKIP_KEY = "skip"
    const val ANIMATION_DELAY = 1000L
    const val ANIMATION_START = 0f
    const val RED_ROTATION = 25f
    const val RED_TRANSLATION_X = 75f
    const val RED_TRANSLATION_Y = -70f
    const val YELLOW_ROTATION = -25f
    const val YELLOW_TRANSLATION_X = -75f
    const val YELLOW_TRANSLATION_Y = -90f
    const val GREEN_TRANSLATION_Y = -170f



}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray())