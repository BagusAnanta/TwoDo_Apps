package com.example.twodoapps.sharePref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class AuthHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val preference = context.getSharedPreferences("authPref", Context.MODE_PRIVATE)

    fun saveToken(token : String){
        preference.edit() { putString("token", token) }
    }

    fun getToken() : String? {
        return preference.getString("token", null)
    }

    fun clearToken() {
        preference.edit { remove("token").apply() }
    }
}