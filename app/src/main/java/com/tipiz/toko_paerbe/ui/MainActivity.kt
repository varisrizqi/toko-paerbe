package com.tipiz.toko_paerbe.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.ui.bottomnav.home.HomeViewModel
import com.tipiz.toko_paerbe.ui.utils.Constant.key_en
import com.tipiz.toko_paerbe.ui.utils.Constant.key_in
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var analytics2: FirebaseAnalytics
    private val fcm: FirebaseMessaging by inject()
    private val remoteConfig: FirebaseRemoteConfig by inject()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        analytics2 = Firebase.analytics

        themeGet()
        getLocalize()

        /*
        * untuk subscribe topic. *(topic from backend) .
        * jika api level android < Android 11 (API level 30) maka hanya butuh subscribe topic saja,
        * tidak membutuhkan permission.POST_NOTIFICATIONS
        */
        fcm.subscribeToTopic(TOPIC_KEY_FIREBASE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            askNotificationPermission()
        }

        setRemoteConfig()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission sudah diberikan
        } else {
            // Meminta izin notifikasi
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    private fun themeGet() {
        val themeChecker = viewModel.getTheme()
        if (themeChecker) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getLocalize() {
        val language = viewModel.getLocalize()
        if (language.equals(key_en, true)) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(key_en))
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(key_in))
        }
    }

    //remoteconfig
    private fun setRemoteConfig(){
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()

    }


    //back notification
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleIntent(it)
        }
    }

    private fun handleIntent(intent: Intent) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.container_main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val destination = intent.getIntExtra("destination", -1)
        if (destination != -1) {
            navController.navigate(destination)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.container_main_nav_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val TOPIC_KEY_FIREBASE = "promo"
    }
}