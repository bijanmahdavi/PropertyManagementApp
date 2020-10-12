package com.example.propertymanagementapp.helpers

import android.content.Context
import com.example.propertymanagementapp.data.model.User
import com.example.propertymanagementapp.ui.home.MainActivity

class TokenManager() {
    private var sharedPreferences = MainActivity.mContext.getSharedPreferences("Tokens", Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    fun storeToken(token: String) {
        editor.putString("TOKEN", token).apply()
        editor.putBoolean("LOGGED_IN", true).apply()
        editor.commit()
    }

    fun getToken(): String {
        return sharedPreferences.getString("TOKEN", "").toString()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("LOGGED_IN", false)
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
}