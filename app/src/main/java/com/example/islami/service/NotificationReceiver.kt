package com.example.islami.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.islami.ui.Constants

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intent1 = Intent(context, PlayService::class.java)
        if (intent.action != null) {
            when (intent.action) {
                Constants.ACTION_PLAY -> {
//                    Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show()
                    intent1.putExtra("action", intent.action)
                    context.startService(intent1)
                }

                Constants.ACTION_NEXT -> {
//                    Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
                    intent1.putExtra("action", intent.action)
                    context.startService(intent1)
                }

                Constants.ACTION_PREVIOUS -> {
//                    Toast.makeText(context, "Previous", Toast.LENGTH_SHORT).show()
                    intent1.putExtra("action", intent.action)
                    context.startService(intent1)
                }
            }
        }
    }
}