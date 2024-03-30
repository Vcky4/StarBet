package com.betstrivepro.datastore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsConstants {
    val IS_FIRST_RUN = booleanPreferencesKey("IS_FIRST_RUN")
    val LANGUAGE = stringPreferencesKey("LANGUAGE")
    val PIN = stringPreferencesKey("PIN")
    val FCM_TOKEN = stringPreferencesKey("FCM_TOKEN")
    val USER_NAME = stringPreferencesKey("USER_NAME")
    val CHAT_ID = stringPreferencesKey("CHAT_ID")
}