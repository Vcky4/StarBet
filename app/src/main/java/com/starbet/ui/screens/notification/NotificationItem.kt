package com.starbet.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starbet.data.db.models.NotificationModel
import com.starbet.ui.theme.CardColor
import com.starbet.ui.theme.TextDeep
import java.text.DateFormat
import java.util.Date

@Composable
fun NotificationItem(item: NotificationModel) {
    val dateTimeFormat: DateFormat =
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    val time = dateTimeFormat.format(Date().time)
    Card(Modifier.padding(vertical = 3.dp, horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .background(CardColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier
                    .height(50.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDeep
                )
                Text(
                    text = item.body,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDeep.copy(alpha = 0.8f)
                )
            }
            Text(
                //convert date to time and format it yyyy-MM-dd HH:mm
                text = time,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = TextDeep
            )
        }
    }
}
    
