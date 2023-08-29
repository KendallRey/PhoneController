package com.phonecontroller.ken.ui.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.phonecontroller.ken.R
import com.phonecontroller.ken.databinding.ActivityMainBinding
import com.phonecontroller.ken.services.ControllerService
import com.phonecontroller.ken.services.FirebaseService
import kotlinx.coroutines.Job
import org.checkerframework.checker.units.qual.C
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var controllerService: ControllerService? = null
    private var isServiceBound = false

    private var job : Job? = null

    private val stopAppReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("TEST","RES")
            if (intent?.action == "STOP_APP") {
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(1234124)

                if (isServiceBound) {
                    unbindService(serviceConnection)
                    isServiceBound = false
                }
                // Perform any necessary cleanup and shutdown operations
                finish() // Finish the activity

                stopService(Intent(this@MainActivity, ControllerService::class.java)) // Stop the service
                // You can also call System.exit(0) to forcefully exit the app

            }
        }
    }

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
            val localBinder = binder as ControllerService.LocalBinder
            controllerService = localBinder.getService()
            Log.e("CONN","CONNECTED!")
            isServiceBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e("CONN","DISCONNECTED!")
            isServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnStartService = binding.btnStartService
        val btnStopService = binding.btnStopService
        val btnTEST = binding.btnTEST

        btnStartService.setOnClickListener {
            Log.e("START","START")
            startService(it)
        }

        btnStopService.setOnClickListener {
            Log.e("STOP","STOP")
            stopServiceOnClick(it)
        }

        btnTEST.setOnClickListener {
            exitProcess(0)
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(stopAppReceiver, IntentFilter("STOP_APP"))
    }


    private fun startService(view : View){
        val serviceIntent = Intent(this, ControllerService::class.java)
        startService(serviceIntent)
//        startForegroundService(serviceIntent)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopServiceOnClick(view: View) {
        val serviceIntent = Intent(this, ControllerService::class.java)
        stopService(serviceIntent)
        unbindService(serviceConnection)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(stopAppReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }


}