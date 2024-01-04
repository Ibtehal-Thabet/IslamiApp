package com.example.islami

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    companion object {
        const val RADIO_NOTIFICATION_CHANNEL = "radio channel"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val desc = getString(R.string.quran)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(RADIO_NOTIFICATION_CHANNEL, name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}