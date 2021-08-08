package com.fastrata.eimprovement.utils

import android.content.Context
import com.fastrata.eimprovement.features.splashscreen.WelcomeMessageModel

internal class PreferenceUtils(context: Context) {

    companion object {
        private const val WELCOME_MESSAGE_PREFS_NAME = "welcome_message_pref"
        private const val IS_DISPLAY = "isDisplay"
    }

    private val welcomeMessagePreferences = context.getSharedPreferences(WELCOME_MESSAGE_PREFS_NAME, Context.MODE_PRIVATE)

    fun setWelcomeMessage(value: WelcomeMessageModel) {
        val editor = welcomeMessagePreferences.edit()
        editor.putBoolean(IS_DISPLAY, value.isDisplay)
        editor.apply()
    }

    fun getWelcomeMessage(): WelcomeMessageModel {
        val model = WelcomeMessageModel()
        model.isDisplay = welcomeMessagePreferences.getBoolean(IS_DISPLAY, false)
        return model
    }

}
