package com.example.islami.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.islami.MyApplication
import com.example.islami.R
import com.example.islami.ui.Constants.ACTION_NEXT
import com.example.islami.ui.Constants.ACTION_PLAY
import com.example.islami.ui.Constants.ACTION_PREVIOUS
import com.example.islami.ui.home.tabs.radio.RadioFragment

class PlayService : Service() {

    private val binder = MyBinder()
    var mediaPlayer = MediaPlayer()
    var isPlaying = false

    private var radioFragment = RadioFragment()

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    inner class MyBinder : Binder() {
        fun getServices(): PlayService {
            return this@PlayService
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//        return super.onStartCommand(intent, flags, startId)
        val urlToPlay = intent.getStringExtra("url")
        val name = intent.getStringExtra("name")
        val action = intent.getStringExtra("action")

        if (urlToPlay != null && name != null) {
            startMediaPlayer(urlToPlay, name)
        }

        if (action != null) {
            Log.e("action", action)
            when (action) {
                ACTION_PLAY -> {
                    radioFragment.playClicked()
                    updateNotification()
                }

                ACTION_NEXT -> {
                    radioFragment.nextClicked()
                    updateNotification()
                }

                ACTION_PREVIOUS -> {
                    radioFragment.prevClicked()
                    updateNotification()
                }
            }
//            if (action.equals("play")){
//                playPauseMediaPlayer()
//                isPlaying = true
//            }else if (action.equals("stop")) {
//                stopMediaPlayer()
//                isPlaying = false
//            }
        }
        return START_NOT_STICKY
    }

    var name = ""
    fun startMediaPlayer(urlToPlay: String?, name: String) {
        pauseMediaPlayer()
        this.name = name
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(this, Uri.parse(urlToPlay))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            it.start()
            isPlaying = true
        }
        createNotificationForMediaPlayer(name)
    }

    fun playPauseMediaPlayer() {
        Log.e("action", "Play Pause")
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            isPlaying = false
        } else if (!mediaPlayer.isPlaying) {
            isPlaying = true
            mediaPlayer.start()
        }
        updateNotification()
    }

    private fun stopMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            isPlaying = false
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        stopForeground(true)
        stopSelf()
    }

    fun pauseMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            isPlaying = false
            mediaPlayer.pause()
        }
    }

    fun createNotificationForMediaPlayer(name: String) {

        val defaultView = RemoteViews(packageName, R.layout.notification_view)
        defaultView.setTextViewText(R.id.title, "Islami Radio")
        defaultView.setTextViewText(R.id.desc, name)
        defaultView.setImageViewResource(R.id.play, R.drawable.ic_pause)

        defaultView.setOnClickPendingIntent(R.id.play, getPlayButtonPendingIntent())
        defaultView.setOnClickPendingIntent(R.id.play_next, getPlayNextButtonPendingIntent())
        defaultView.setOnClickPendingIntent(R.id.play_previous, getPlayPrevButtonPendingIntent())

        val builder = NotificationCompat.Builder(this, MyApplication.RADIO_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.logo)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(defaultView)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSound(null)

        startForeground(20, builder.build())

    }

    fun updateNotification() {
        val defaultView = RemoteViews(packageName, R.layout.notification_view)
        defaultView.setTextViewText(R.id.title, "Islami Radio")
        defaultView.setTextViewText(R.id.desc, name)
        defaultView.setImageViewResource(
            R.id.play,
            if (isPlaying) R.drawable.ic_pause else R.drawable.play
        )
        defaultView.setOnClickPendingIntent(R.id.play, getPlayButtonPendingIntent())
        defaultView.setOnClickPendingIntent(R.id.play_next, getPlayNextButtonPendingIntent())
        defaultView.setOnClickPendingIntent(R.id.play_previous, getPlayPrevButtonPendingIntent())

        val builder = NotificationCompat.Builder(this, MyApplication.RADIO_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.logo)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(defaultView)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setDefaults(0)
            .setSound(null)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(20, builder.build())
    }

    private fun getPlayButtonPendingIntent(): PendingIntent? {
        val intent = Intent(this, NotificationReceiver::class.java).setAction(ACTION_PLAY)
        intent.putExtra("action", ACTION_PLAY)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 12, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return pendingIntent
    }

    private fun getPlayNextButtonPendingIntent(): PendingIntent? {
        val intent = Intent(this, NotificationReceiver::class.java).setAction(ACTION_NEXT)
        intent.putExtra("action", ACTION_NEXT)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 12, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return pendingIntent
    }

    private fun getPlayPrevButtonPendingIntent(): PendingIntent? {
        val intent = Intent(this, NotificationReceiver::class.java).setAction(ACTION_PREVIOUS)
        intent.putExtra("action", ACTION_PREVIOUS)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 12, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return pendingIntent
    }

    fun setCallBack(radioFragment: RadioFragment) {
        this.radioFragment = radioFragment
    }
}