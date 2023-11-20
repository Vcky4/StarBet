package com.starbet.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.openTelegram(userName: String) {
    try {
        val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$userName"))
        startActivity(telegram)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
            )
        )
    } catch (t: Throwable) {
        Toast.makeText(
            this,
            "Request failed try again: $t",
            Toast.LENGTH_LONG
        ).show()
    }
}