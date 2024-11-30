package com.example.sharedreferencenotif

import android.content.Context

class PrefManager private constructor(cotexts: Context){
    private val sharedPreferences = cotexts.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "AuthAppPrev"
        private var KEY_USERNAME = "username"

        @Volatile
        private var instance: PrefManager? = null
        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                        prev -> instance = prev
                }
            }
        }
    }
    fun saveUsername(username: String) {
        sharedPreferences.edit().also {
            it.putString(KEY_USERNAME, username)
            it.apply()
        }
    }

    fun getUsername():String{
        return sharedPreferences.getString(KEY_USERNAME, "") ?: ""
    }

    fun clear(){
        sharedPreferences.edit().also {
            it.clear()
            it.apply()
        }
    }
}