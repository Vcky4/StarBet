package com.starbettipz.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starbettipz.R
import com.starbettipz.data.db.models.TipModel
import com.starbettipz.ui.theme.CardColor
import com.starbettipz.ui.theme.CardColor2
import com.starbettipz.ui.theme.Primary
import com.starbettipz.ui.theme.Secondary
import com.starbettipz.ui.theme.TextDeep

@Composable
fun DetailItem(item: TipModel, onClick: () -> Unit = {}) {
    val context = LocalContext.current
    //format date to DD/MM/YYYY
    val time = item.date
//        "${Date(item.date).date}/${Date(item.date).month.plus(1)}/${Date(item.date).year.plus(1900)}"


    Card(
        Modifier
            .clickable { onClick.invoke() }
            .padding(vertical = 8.dp, horizontal = 14.dp)) {
        Column(
            Modifier
                .background(CardColor, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = time, fontSize = 18.sp,
                color = Secondary,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 14.dp)
                    .align(Alignment.End),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(Secondary, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.outlined_flag),
                        contentDescription = "flag",
                        tint = Primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.league, fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(0.9f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.home, fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDeep,
                        modifier = Modifier.weight(0.5f),
//                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = item.homeScore, fontSize = 16.sp,
                            color = TextDeep,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            painter = painterResource(
                                id = when (item.status) {
                                    "won" -> R.drawable.check_circle
                                    "lost" -> R.drawable.cancel_fill
                                    else -> R.drawable.outlined_flag
                                }
                            ),
                            contentDescription = "flag",
                            tint = Primary
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = item.awayScore, fontSize = 16.sp,
                            color = TextDeep,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Text(
                        text = item.away, fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDeep,
                        modifier = Modifier.weight(0.5f),
                        textAlign = TextAlign.End,
//                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = CardColor,
                        ),
                        modifier = Modifier,
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = item.prediction, fontSize = 14.sp,
                            color = TextDeep,
                            modifier = Modifier
                                .padding(vertical = 6.dp, horizontal = 6.dp)
                        )
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = CardColor2,
                        ),
                        modifier = Modifier,
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = item.odd, fontSize = 14.sp,
                            color = TextDeep,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(vertical = 6.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }
    }
}