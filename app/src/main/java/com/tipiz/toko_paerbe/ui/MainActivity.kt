package com.tipiz.toko_paerbe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.ui.bottomnav.home.HomeViewModel
import com.tipiz.toko_paerbe.ui.utils.Constant.key_en
import com.tipiz.toko_paerbe.ui.utils.Constant.key_in
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        themeGet()
        getLocalize()
    }

    private fun themeGet() {
            val themeChecker = viewModel.getTheme()
            if (themeChecker) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
    }

    private fun getLocalize(){
        val language = viewModel.getLocalize()
        if (language.equals(key_en, true)) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(key_en))
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(key_in))
        }
    }
}