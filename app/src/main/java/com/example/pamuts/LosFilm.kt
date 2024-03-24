package com.example.pamuts

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LosFilm: Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        var instance: LosFilm? = null
            private set

        val context: Context?
            get() = instance

    }
}