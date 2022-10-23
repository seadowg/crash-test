package com.example.myapplication

import android.app.IntentService
import android.content.Intent

class CrashService() : IntentService("CrashService") {
    override fun onHandleIntent(p0: Intent?) {
        throw RuntimeException()
    }
}
