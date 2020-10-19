package com.example.propertymanagementapp.helpers

import android.content.Context
import com.example.propertymanagementapp.data.model.User
import com.example.propertymanagementapp.ui.home.MainActivity

class TokenManager() {
    private var sharedPreferences = MainActivity.mContext.getSharedPreferences("Tokens", Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    fun storeToken(token: String, id: String) {
        editor.putString("TOKEN", token).apply()
        editor.putString("_ID", id)
        editor.putBoolean("LOGGED_IN", true).apply()
        editor.commit()
    }

    fun storeImageLocation(location: String) {
        editor.putString("LOCATION", location)
    }

    fun getImageLocation(): String {
        return sharedPreferences.getString("LOCATION", "").toString()
    }

    fun getToken(): String {
        return sharedPreferences.getString("TOKEN", "").toString()
    }

    fun getUserId(): String {
        return sharedPreferences.getString("_ID", "").toString()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("LOGGED_IN", false)
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
}