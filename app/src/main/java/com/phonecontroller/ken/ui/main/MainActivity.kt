package com.phonecontroller.ken.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.phonecontroller.ken.R
import com.phonecontroller.ken.services.FirebaseService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startService(view : View){
        val serviceIntent = Intent(this, FirebaseService::class.java)
        startService(serviceIntent)
    }

    fun stopServiceOnClick(view: View) {
        val serviceIntent = Intent(this, FirebaseService::class.java)
        stopService(serviceIntent)
    }
}