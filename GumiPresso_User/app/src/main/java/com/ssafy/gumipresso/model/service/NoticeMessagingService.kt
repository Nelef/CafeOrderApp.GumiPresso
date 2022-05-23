package com.ssafy.gumipresso.model.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.util.NoticeMessageUtil

private const val TAG ="NoticeMessagingService"
class NoticeMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification.let {
            val messageTitle = it!!.title
            val messageContent = it!!.body

            NoticeMessageUtil.setMessageToSharedPreference(messageContent.toString())

            val mainIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, 0)
            val builder = NotificationCompat.Builder(this, "ssafy_id")
                .setSmallIcon(R.mipmap.ic_launcher_new)
                .setContentTitle(messageTitle)
                .setContentText(messageContent)
                .setAutoCancel(true)
                .setContentIntent(mainPendingIntent)

            NotificationManagerCompat.from(this).apply {
                notify(101,builder.build())
            }

        }
    }
}