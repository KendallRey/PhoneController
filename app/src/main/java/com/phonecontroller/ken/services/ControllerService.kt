package com.phonecontroller.ken.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.phonecontroller.ken.R
import com.phonecontroller.ken.ui.main.MainActivity


class ControllerService : Service() {

    private val binder = LocalBinder()
    private val NOTIFICATION_ID = 1234124
    private val CHANNEL_ID = "NOTIFICATION_CHANNEL_31231"

    inner class LocalBinder : Binder() {
        fun getService(): ControllerService = this@ControllerService
    }
    override fun onCreate() {
        super.onCreate()
//        startNotificationListener()
        // Initialize your service here
        Log.e("==============","START")
//        val notification = createNotification("Start")
        Log.e("1","START_1")

//        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "STOP_SERVICE") {
            stopSelf() // Stop the service when the stop button is clicked
            return START_NOT_STICKY
        }
        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val notificationBuilder: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
//            .setContentTitle("Counting steps")
//            .setContentText("REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentIntent(pendingIntent)
//        val notification = notificationBuilder.build()
//        Log.e("2",notification.channelId)
//        startForeground(NOTIFICATION_ID, notification)
        createNotification(this,"RERE","AAAAAAAAAAAAAAAAAAA")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup and stop any ongoing tasks
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }


    private fun createNotification(message : String): Notification {
        // Create a notification channel (for Android Oreo and above)
        Log.e("2","START_2")

        val channel = NotificationChannel(
            "notification_service_123132313123",
            "Location Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        Log.e("3","START_3")
            // Build the notification
        val notificationIntent = Intent(this, MainActivity::class.java) // Replace with your main activity
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            Log.e("4", "START_4")

        Log.e("5",notificationManager.notificationChannels.size.toString())
            val notif = NotificationCompat.Builder(this, channel.id)
                .setContentTitle("Location Service")
                .setContentText("Fetching your location")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line..."))
                .build()
            Log.e("6",notif.channelId.toString())
            return notif

    }

    private fun createNotificationChannel() {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Counting steps",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableVibration(false)
        channel.setSound(null, null)
        channel.setShowBadge(false)
        mNotificationManager.createNotificationChannel(channel)
    }

    fun createNotification(
        context: Context,
        title: String,
        content: String,
    ) {
        val stopAppIntent = Intent("STOP_APP")
        val stopAppPendingIntent = PendingIntent.getBroadcast(this, 0, stopAppIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        val notification = builder
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_baseline_stop_circle_24, "Stop Service", stopAppPendingIntent)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    fun showNotification(message : String){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_ID, createNotification(message))
    }
}