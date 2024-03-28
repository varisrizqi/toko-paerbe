package com.tipiz.toko_paerbe.ui.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.Base64

object Constant {

    //SplashScreen Fragment
    const val ANIMATION_DELAY = 1000L
    const val ANIMATION_START = 0f
    const val RED_ROTATION = 25f
    const val RED_TRANSLATION_X = 75f
    const val RED_TRANSLATION_Y = -70f
    const val YELLOW_ROTATION = -25f
    const val YELLOW_TRANSLATION_X = -75f
    const val YELLOW_TRANSLATION_Y = -90f
    const val GREEN_TRANSLATION_Y = -170f

    //profile fragment
    const val CAMERA_PERMISSION_CODE = 111
    const val GALLERY_PERMISSION_CODE = 151

    //home fragment
    const val key_en = "en"
    const val key_in = "in"

    const val extra_detail ="extra_detail"
    const val FLAG_TRANSACTION = "transaction"





}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray())

fun showToast(context:Context,value:String){
    Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
}