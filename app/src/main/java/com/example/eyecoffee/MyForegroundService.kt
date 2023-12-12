package com.example.eyecoffee

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "running channel")
            .setSmallIcon(R.drawable.baseline_coffee_24)
            .setContentTitle("Execução está ativa")
            .setContentText("Tempo restante: 00:59 ")
            .build()
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }

}

