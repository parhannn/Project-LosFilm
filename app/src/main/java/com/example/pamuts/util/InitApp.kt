package com.example.pamuts.util

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class InitApp : Application() {
    private var isNightModeEnabled = false
    override fun onCreate() {
        super.onCreate()
        singleton = this
        val mPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false)
    }

    fun isNightModeEnabled(): Boolean {
        return isNightModeEnabled
    }

    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
        this.isNightModeEnabled = isNightModeEnabled
        val mPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mPrefs.edit()
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled)
        editor.apply()
    }

    companion object {
        const val NIGHT_MODE = "NIGHT_MODE"
        private var singleton: InitApp? = null
        val instance: InitApp?
            get() {
                if (singleton == null) {
                    singleton = InitApp()
                }
                return singleton
            }
    }
}