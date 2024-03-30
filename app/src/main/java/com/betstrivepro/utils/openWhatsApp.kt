package com.betstrivepro.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openWhatsApp(phoneNumber: String) {
    try {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hello Swapz")
            putExtra(
                "jid",
                "$phoneNumber@s.whatsapp.net"
            )
            type = "text/plain"
            setPackage("com.whatsapp")
        }
        startActivity(sendIntent)
    } catch (e: Exception) {
        e.printStackTrace()
        val appPackageName = "com.whatsapp"
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}
