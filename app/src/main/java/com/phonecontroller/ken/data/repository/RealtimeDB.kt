package com.phonecontroller.ken.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class RealtimeDB(path : String) {
    private val db = FirebaseDatabase.getInstance("https://phonecontroller-f013e-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val databaseReference = db.getReference(path)

    fun get() : Query {
        return databaseReference.orderByKey()
    }
}