package com.tipiz.toko_paerbe.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.tipiz.core.domain.model.firebase.Notification
import com.tipiz.core.domain.model.firebase.PromoFcm
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.ui.MainActivity
import com.tipiz.toko_paerbe.ui.utils.Constant.CHANNEL_ID
import com.tipiz.toko_paerbe.ui.utils.Constant.CHANNEL_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SetupFirebaseMessaging : FirebaseMessagingService() {


    private val useCase: TokoUseCase by inject()
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        /*
        * test fcm from firebase console
        * */
        /* if (message.notification != null){
             sendNotification(message)
         }*/

        if (message.data.isNotEmpty()) {
            val gson = Gson()
            val promoString = gson.toJson(message.data)
            val promo = gson.fromJson(promoString, PromoFcm::class.java)
            Log.d("notificationFCM", "$promo")
            CoroutineScope(Dispatchers.IO).launch {
                useCase.insertNotification(promo)
                val lastData = useCase.getAllNotification().first()
                sendNotification(lastData[lastData.lastIndex])
            }
        }

    }


    private fun sendNotification(data: Notification) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(data.title)
            .setContentText(data.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(createContentIntent())
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notificationID = data.id
        notificationManager.notify(notificationID, notificationBuilder.build())
    }

    /*
    * original code for remember me
    * */
    /* private fun createContentIntent(): PendingIntent {
         return NavDeepLinkBuilder(this)
             .setComponentName(MainActivity::class.java)
             .setGraph(R.navigation.main_nav)
             .setDestination(R.id.notificationFragment)
             .setArguments(null)
             .createPendingIntent()
     }*/

   private fun createContentIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("destination", R.id.notificationFragment)
        }
        return PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


}