package com.phonecontroller.ken.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginRepository {

    var fireBaseAuth = Firebase.auth

    // in-memory cache of the loggedInUser object
    fun login(email: String, password: String): Task<AuthResult> {
        return fireBaseAuth.signInWithEmailAndPassword(email,password)
    }
}