package com.starbet.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starbet.R
import com.starbet.ui.theme.Secondary
import com.starbet.ui.theme.TextDeep
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun Announcement(navigator: DestinationsNavigator) {
//    val viewModel: AdminViewModel = koinViewModel()
//    val path = stringResource(id = R.string.announcement)
//    LaunchedEffect(key1 = Unit) {
//        viewModel.getAnnouncement(path)
//    }
//
//    val announcement = viewModel.announcement.observeAsState().value
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Column(
//            Modifier
//                .background(Secondary, shape = MaterialTheme.shapes.medium)
//                .fillMaxWidth(0.9f)
//                .fillMaxHeight(0.8f)
//                .scrollable(
//                    orientation = Orientation.Vertical,
//                    state = rememberScrollState()
//                )
//                .padding(vertical = 16.dp, horizontal = 8.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = announcement?.title ?: stringResource(id = R.string.announcement),
//                fontSize = 30.sp,
//                fontWeight = FontWeight.Bold,
//                color = TextDeep,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = announcement?.announcement ?: "",
//                fontSize = 22.sp,
//                color = TextDeep,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxHeight(0.9f)
//                    .fillMaxWidth()
//            )
//        }
//    }
}