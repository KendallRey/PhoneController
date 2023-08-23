package com.phonecontroller.ken.services

import android.app.Service
import android.content.Intent
import android.os.IBinder


class FirebaseService : Service() {
    override fun onCreate() {
        super.onCreate()
        // Initialize your service here
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Perform background tasks here
        return START_STICKY // Or other appropriate return value
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // If your service doesn't support binding
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}