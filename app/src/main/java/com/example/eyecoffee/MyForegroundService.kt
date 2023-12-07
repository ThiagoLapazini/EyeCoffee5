package com.example.eyecoffee

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyForegroundService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            while (true) {
                Log.e("Service Foreground", "Funcionando eternamente")
                Thread.sleep(5000)
            }
        }.start()

        
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
