package com.example.twodoapps

import android.app.Application
import com.example.twodoapps.cookieJar.PersistentCookieJar
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class twoDoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}