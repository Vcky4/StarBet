package com.starbet.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.starbet.R
import com.starbet.ui.screens.destinations.DetailScreenDestination
import com.starbet.ui.screens.destinations.VipPinDestination
import com.starbet.ui.theme.Primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun Home(navigator: DestinationsNavigator) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = koinViewModel()
    val freeItems = homeViewModel.freeItems
    val vipItems = homeViewModel.vipItems
    val liveItems = homeViewModel.liveItems
    val contactItems = homeViewModel.contactItems
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val whatsapp = homeViewModel.whatsApp.observeAsState().value
    val telegram = homeViewModel.telegram.observeAsState().value
    val email = homeViewModel.email.observeAsState().value

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "logo",
            modifier = Modifier
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(pageCount = 2, state = pagerState) { page ->
            when (page) {
                0 -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(
                            items = freeItems,
                        ) {
                            val title = when (it.title) {
                                R.string.dialy_sure_tips -> "Daily Sure Tips"
                                R.string.football_tips -> "Football tips"
                                R.string.basketball_tips -> "Basketball tips"
                                else -> "Tennis tips"
                            }
                            HomeItem(it) {
                                navigator.navigate(DetailScreenDestination(title))
                            }
                        }
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(
                            items = vipItems,
                        ) {
                            val title = when (it.title) {
                                R.string.previous_draws_results -> "Previous Draws Results"
                                else -> "Previous Correct Score"
                            }
                            HomeItem(it) {
                                if (it.title == R.string.previous_correct_score
                                    || it.title == R.string.previous_draws_results
                                ) {
                                    navigator.navigate(DetailScreenDestination(title))
                                } else {
                                    navigator.navigate(VipPinDestination(title))
                                }
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
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
                Text(text = stringResource(id = R.string.free), fontSize = 20.sp)
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
                Text(text = stringResource(id = R.string.free), fontSize = 20.sp)
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
