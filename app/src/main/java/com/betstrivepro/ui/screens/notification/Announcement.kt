package com.betstrivepro.ui.screens.notification

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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