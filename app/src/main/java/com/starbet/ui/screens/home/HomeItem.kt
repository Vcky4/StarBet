package com.starbet.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starbet.R
import com.starbet.data.db.models.HomeItemModel
import com.starbet.ui.theme.Primary

@Composable
fun HomeItem(item: HomeItemModel, onClick: () -> Unit) {
    Card(Modifier
        .clickable { onClick.invoke() }
        .height(150.dp)
        .padding(vertical = 8.dp, horizontal = 14.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Primary
        )
        ) {
        Column(
            modifier = Modifier
                .background(Primary, CircleShape)
                .fillMaxSize()
                .clip(CircleShape)
                .padding(horizontal = 14.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = item.image),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = item.title) ,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}
    
