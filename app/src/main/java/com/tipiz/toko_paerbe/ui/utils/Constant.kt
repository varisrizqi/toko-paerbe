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

    //detail store
    const val extra_detail = "extra_detail"
    const val extra_variant = "extra_variant"
    const val extra_base = "extra_base"
    const val extra_chip = "extra_chip"
    const val extra_setImage = "extra_setImage"
    const val FLAG_TRANSACTION = "transaction"
    const val PAGING_PAGE_LIMIT = 10
    const val PAGING_PAGE = 1
    const val RAM_16_GB = "RAM 16GB"
    const val INDONESIA_CURRENCY = "IDR"
    const val extra_checkout = "extra_checkout"

    //bottomSheet
    const val extra_btm_sheet = "extra_bottom_sheet"
     const val BUNDLE_KEY_SORT = "sort"
     const val BUNDLE_KEY_CATEGORY = "category"
     const val BUNDLE_KEY_LOWEST = "lowest"
     const val BUNDLE_KEY_HIGHEST = "highest"
     const val BUNDLE_KEY_SEARCH = "search"
     const val REQUEST_KEY_BOTTOM_SHEET = "filter_data_bs"

    //Chart
    const val CART_FULL = "cartFull"
    const val CART_ADDED = "cartAdded"
    const val CART_DECREASED = "cartDecreased"
    const val CART_MINIMUM = "cartMinimum"

}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray()) ?: ""

fun showToast(context: Context, value: String) {
    Toast.makeText(context, value, Toast.LENGTH_LONG).show()
}

