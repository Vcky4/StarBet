package com.starbettipz.data.db.models

data class NotificationModel(
    val id: Int,
    val title: String,
    val body: String,
    val date: Long = System.currentTimeMillis(),
)