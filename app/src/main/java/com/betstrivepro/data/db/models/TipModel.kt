package com.betstrivepro.data.db.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

//@Entity("tips")
@IgnoreExtraProperties
data class TipModel(
//    @PrimaryKey(autoGenerate = true)
    val id: String,
    val league: String,
    val home: String,
    val away: String,
    val homeScore: String,
    val awayScore: String,
    val odd: String,
    val date: String = "21/11/2023",
    val status: String,
    val prediction: String,
) {
    constructor() : this("", "", "", "", "", "", "", "21/11/2023", "", "")

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "key" to id,
            "league" to league,
            "home" to home,
            "away" to away,
            "homeScore" to homeScore,
            "awayScore" to awayScore,
            "odd" to odd,
            "date" to date,
            "status" to status,
            "prediction" to prediction
        )
    }
}
