package com.example.eyecoffee.api

import android.content.Context
import android.content.SharedPreferences
import com.example.eyecoffee.R

class SessionManager (context: Context?) {

    private var prefs: SharedPreferences = context!!.getSharedPreferences(context.getString(R.string.eye_coffee), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "eyemobile.dev"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}