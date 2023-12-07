package com.example.eyecoffee

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MyService : Service() {
    private lateinit var player: MediaPlayer

    override fun onStartCommand(init: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, R.raw.teste)
        player.isLooping
        player.start()
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}