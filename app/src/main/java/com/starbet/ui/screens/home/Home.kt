package com.starbet.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.starbet.R
import com.starbet.ui.screens.destinations.ChatDestination
import com.starbet.ui.screens.destinations.ConversationsDestination
import com.starbet.ui.screens.destinations.DetailScreenDestination
import com.starbet.ui.screens.destinations.VipPinDestination
import com.starbet.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun Home(navigator: DestinationsNavigator) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = koinViewModel()
    val freeItems = homeViewModel.freeItems
    val vipItems = homeViewModel.vipItems
    var clickCount by remember { mutableStateOf(0) }
    var lastClickTime by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (System.currentTimeMillis() - lastClickTime >= 3000) {
                clickCount = 0 // Reset click count if no new click after 3 seconds
            }
        }
    }

    //get screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp


    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "logo",
            modifier = Modifier
                .height(100.dp)
                .clickable {
                    lastClickTime = System.currentTimeMillis()
                    clickCount++
                    if (clickCount >= 15) {
                        clickCount = 0 // Reset click count
                        navigator.navigate(ConversationsDestination())
                    }
                }
        )
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(pageCount = 2, state = pagerState) { page ->
            when (page) {
                0 -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.height(screenHeight.times(0.65).dp)
                    ) {
                        items(
                            items = freeItems,
                        ) {
                            val title = stringResource(id = it.title)
                            HomeItem(it) {
                                if (it.id == 6) {
                                    navigator.navigate(ChatDestination())
                                } else {
                                    navigator.navigate(DetailScreenDestination(title))
                                }
                            }
                        }
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.height(screenHeight.times(0.65).dp)
                    ) {
                        items(
                            items = vipItems,
                        ) {
                            val title = stringResource(id = it.title)
                            HomeItem(it) {
                                if (it.title == R.string.previous_correct_score
                                    || it.title == R.string.previouss_draws_vip
                                ) {
                                    navigator.navigate(DetailScreenDestination(title))
                                } else if (it.id == 3) {
                                    navigator.navigate(ChatDestination())
                                } else {
                                    navigator.navigate(VipPinDestination(title))
                                }
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pagerState.currentPage == 0) Primary else Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.free),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Button(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pagerState.currentPage == 1) Primary else Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.vip),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier.heightIn(max = 200.dp)
//        ) {
//            items(
//                items = liveItems,
//            ) {
//                HomeItem(it) {
//                    if (it.id == 5) {
//                        uriHandler.openUri("https://www.livescore.com")
//                    } else {
//                        uriHandler.openUri("https://www.livesports088.com")
//                    }
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(
//            text = stringResource(id = R.string.contact_us),
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier
//                .padding(start = 16.dp)
//                .fillMaxWidth(),
//            textAlign = TextAlign.Center
//        )
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier.heightIn(max = 200.dp)
//        ) {
//            items(
//                items = contactItems,
//            ) {
//                HomeItem(it) {
//                    when (it.id) {
//                        5 -> context.openWhatsApp(whatsapp ?: "")
//                        6 -> context.sendMail(
//                            email ?: "",
//                            "Live Sports Betting Tips"
//                        )
//
//                        8 -> navigator.navigate(ChatDestination())
//
//                        else -> context.openTelegram(telegram ?: "")
//                    }
//                }
//            }
//        }
    }
}
