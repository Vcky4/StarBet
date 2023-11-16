package com.starbet.ui.screens.home

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lsbt.livesportsbettingtips.R
import com.starbet.ui.screens.home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun DetailScreen(trigger: String, navigator: DestinationsNavigator) {
    val viewModel: HomeViewModel = koinViewModel()
    LaunchedEffect(key1 = viewModel) {
        viewModel.getTips(trigger)
    }
    val tips = viewModel.tips.observeAsState(listOf()).value
    val prev = stringResource(id = R.string.previous_correct_score)
    val prev2 = stringResource(id = R.string.previous_draws_results)
    val history = when (trigger) {
        prev -> tips.sortedByDescending { it.date }
        prev2 -> tips.sortedByDescending { it.date }
        else -> tips.filter { !DateUtils.isToday(it.date) }.sortedByDescending { it.date }
    }
    Column {
        Row(
            modifier = Modifier.padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.White
                )
            }
            Text(
                text = when (trigger) {
                    prev -> stringResource(id = R.string.correct_scores)
                    prev2 -> stringResource(id = R.string.fixed_draws)
                    else -> trigger
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        LazyColumn {

            if (trigger != prev && trigger != prev2) {
                item {
                    Text(
                        text = stringResource(id = R.string.today),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                if (tips.none { DateUtils.isToday(it.date) }) {
                    item {
                        Text(
                            text = stringResource(id = R.string.no_tips_available),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 30.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(
                        items = tips.filter { DateUtils.isToday(it.date) }
                    ) {
                        DetailItem(it)
                    }
                }
            }
            item {
                Text(
                    text = stringResource(id = R.string.history),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            if (history.isEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.no_tips_history_available),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 30.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(
                    items = history
                ) {
                    DetailItem(it)
                }
            }
        }
    }

}