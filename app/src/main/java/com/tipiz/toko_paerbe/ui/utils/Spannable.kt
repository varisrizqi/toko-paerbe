package com.tipiz.toko_paerbe.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.ui.utils.Constant.key_in

/*
object Spannable {

    private const val TERMS_CONDITION = "Terms & Conditions"
    private const val URL_TERMS = "https://phincon.com/"
    private const val SYARAT_KETENTUAN = "Syarat & Ketentuan"
    private const val PRIVACY_POLICY = "Privacy Policy"
    private const val URL_PRIVACY = "https://google.com/"
    private const val KEBIJAKAN_PRIVASI = "Kebijakan Privasi"

    fun applyCustomTextColor(locale: String, context: Context, fullText: String): SpannableString {
        val spannableString = SpannableStringBuilder(fullText)

        val tncText = if (locale == key_in) SYARAT_KETENTUAN else TERMS_CONDITION
        val startTnc = fullText.indexOf(tncText)
        val endtnc = startTnc + tncText.length

        val policyText = if (locale== key_in) KEBIJAKAN_PRIVASI else PRIVACY_POLICY
        val startPolicy = fullText.indexOf(policyText)
        val endPolicy = startPolicy + policyText.length

        val customColor = ContextCompat.getColor(context, R.color.primary)

        val greenClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                // Handle click for "Syarat & Ketentuan"
                openUrl(context, URL_TERMS)
            }
        }
        val blueClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                // Handle click for "Kebijakan Privasi"
                openUrl(context, URL_PRIVACY)
            }
        }
        spannableString.setSpan(greenClickableSpan, startTnc, endtnc, 0)
        spannableString.setSpan(blueClickableSpan, startPolicy, endPolicy, 0)

        spannableString.setSpan(
            ForegroundColorSpan(customColor),
            startTnc,
            endtnc,
            0
        )
        spannableString.setSpan(
            ForegroundColorSpan(customColor),
            startPolicy,
            endPolicy,
            0
        )

        return SpannableString(spannableString)

    }


    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}*/