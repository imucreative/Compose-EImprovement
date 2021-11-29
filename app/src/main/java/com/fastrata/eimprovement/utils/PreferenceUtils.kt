package com.fastrata.eimprovement.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import android.text.TextUtils
import com.fastrata.eimprovement.features.splashscreen.WelcomeMessageModel
import com.google.gson.Gson

internal class PreferenceUtils(context: Context) {

    private val backedUpPreferences: SharedPreferences? = context.getSharedPreferences(BACKED_UP_NAME, Context.MODE_PRIVATE)
    private val nonBackedUpPreferences: SharedPreferences? = context.getSharedPreferences(NON_BACKED_UP_NAME, Context.MODE_PRIVATE)
    private val welcomeMessagePreferences = context.getSharedPreferences(WELCOME_MESSAGE_PREFS_NAME, Context.MODE_PRIVATE)
    private val gson by lazy { Gson() }

    companion object {
        private const val WELCOME_MESSAGE_PREFS_NAME = "welcome_message_pref"
        private const val BACKED_UP_NAME = "com.fastrata.eimprovement.BACKED_UP"
        private const val NON_BACKED_UP_NAME = "com.fastrata.eimprovement.NON_BACKED_UP"
    }

    fun <T : Any> get(key: String, default: T, fromBackedUp: Boolean = true): T? {
        val type = default::class.java
        val preferences = if (fromBackedUp) backedUpPreferences else nonBackedUpPreferences
        if (preferences != null) {
            return when (default) {
                is String -> type.cast(preferences.getString(key, default))
                is Long -> type.cast(preferences.getLong(key, default))
                is Int -> type.cast(preferences.getInt(key, default))
                is Float -> type.cast(preferences.getFloat(key, default))
                is Boolean -> type.cast(preferences.getBoolean(key, default))
                is Parcelable -> gson.fromJson(preferences.getString(key, ""), type)
                else -> default
            }
        }
        return default
    }

    fun <T : Any> set(key: String, value: T, toBackedUp: Boolean = true): Boolean {
        val preferences = if (toBackedUp) backedUpPreferences else nonBackedUpPreferences
        if (preferences != null && !TextUtils.isEmpty(key)) {
            val editor = preferences.edit()
            when (value) {
                is String -> editor.putString(key, value)
                is Long -> editor.putLong(key, value)
                is Int -> editor.putInt(key, value)
                is Float -> editor.putFloat(key, value)
                is Boolean -> editor.putBoolean(key, value)
                is Parcelable -> editor.putString(key, gson.toJson(value))
                else -> return false
            }
            return editor.commit()
        }
        return false
    }

    fun setWelcomeMessage(key: String, value: WelcomeMessageModel) {
        val editor = welcomeMessagePreferences.edit()
        editor.putBoolean(key, value.isDisplay)
        editor.apply()
        /*val gson = Gson()
        val json = gson.toJson(value)
        set(key, json)*/
    }

    fun getWelcomeMessage(key: String): WelcomeMessageModel {
        val model = WelcomeMessageModel()
        // change default value to false, if you will activated welcome message
        model.isDisplay = welcomeMessagePreferences.getBoolean(key, true)
        return model

        /*val serializedObject = get(key, "", true)
        if (serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<WelcomeMessageModel>() {}.type
            return gson.fromJson(serializedObject, type)
        }
        return null*/
    }

    fun clearAll() {
        backedUpPreferences?.edit()?.clear()?.apply()
        nonBackedUpPreferences?.edit()?.clear()?.apply()
    }

    fun getStringPreference(key: String, defaultValue: String = ""): String? = get(key, defaultValue)
    fun getLongPreference(key: String, defaultValue: Long = 0L): Long? = get(key, defaultValue)
    fun getIntegerPreference(key: String, defaultValue: Int = 0): Int? = get(key, defaultValue)
    fun getBooleanPreference(key: String, defaultValue: Boolean = false): Boolean? = get(key, defaultValue)

    fun setStringPreference(key: String, value: String): Boolean = set(key, value)
    fun setLongPreference(key: String, value: Long): Boolean = set(key, value)
    fun setIntegerPreference(key: String, value: Int): Boolean = set(key, value)
    fun setBooleanPreference(key: String, value: Boolean): Boolean = set(key, value)

}
