package com.starbet.navigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.os.LocaleListCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.starbet.R
import com.starbet.ui.screens.NavGraphs
import com.starbet.ui.screens.appCurrentDestinationAsState
import com.starbet.ui.screens.destinations.AnnouncementDestination
import com.starbet.ui.screens.destinations.Destination
import com.starbet.ui.screens.destinations.NotificationsDestination
import com.starbet.ui.screens.destinations.PdfDisplayDestination
import com.starbet.ui.screens.home.HomeViewModel
import com.starbet.ui.screens.startAppDestination
import com.starbet.ui.theme.Background
import com.starbet.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun NavHost() {
    val navController = rememberAnimatedNavController()
    val homeViewModel: HomeViewModel = koinViewModel()
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    var isContactsOpen by remember {
        mutableStateOf(false)
    }
    val whatsapp = homeViewModel.whatsApp.observeAsState("").value
    val telegram = homeViewModel.telegram.observeAsState("").value
    val email = homeViewModel.email.observeAsState("").value
    var openDialog by remember {
        mutableStateOf(false)
    }
    val isFirstTime = homeViewModel.isFirstTime.observeAsState(false).value
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
    LaunchedEffect(key1 = isFirstTime) {
        if (isFirstTime) {
            openDialog = true
            homeViewModel.setFirstTime()
        }
    }


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    if (currentDestination == NotificationsDestination
                        || currentDestination == PdfDisplayDestination
                        || currentDestination == AnnouncementDestination
                    ) {
                        navController.navigateUp()
                    } else {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(
                            id = if (currentDestination == NotificationsDestination
                                || currentDestination == PdfDisplayDestination
                                || currentDestination == AnnouncementDestination
                            ) {
                                R.drawable.arrow_back
                            } else {
                                R.drawable.menu
                            }
                        ),
                        contentDescription = "menu",
                        tint = Background
                    )
                }
                Text(
                    stringResource(id = R.string.app_name),
                    color = Background,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                if (currentDestination != NotificationsDestination
                    && currentDestination != AnnouncementDestination
                ) {
                    IconButton(onClick = {
                        navController.navigate(AnnouncementDestination)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.notifications),
                            contentDescription = "notification",
                            tint = Background
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        },
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier
                .blur(11.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            engine = rememberAnimatedNavHostEngine(),
            modifier = Modifier.padding(it),
        )
        if (openDialog) {
            Dialog(
                onDismissRequest = {
                    openDialog = false
                }
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Choose language",
                            style = MaterialTheme.typography.headlineLarge
                        )

                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.create(
                                        Locale("en")
                                    )
                                )
                                openDialog = false
                            }
                        ) {
                            Text(text = "English")
                        }

                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.create(
                                        Locale("es")
                                    )
                                )
                                openDialog = false
                            }
                        ) {
                            Text(text = "Spanish")
                        }

                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.create(
                                        Locale("fr")
                                    )
                                )
                                openDialog = false
                            }
                        ) {
                            Text(text = "French")
                        }

                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.create(
                                        Locale("pt")
                                    )
                                )
                                openDialog = false
                            }
                        ) {
                            Text(text = "Portuguese")
                        }

                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.getEmptyLocaleList()
                                )
                                openDialog = false
                            }
                        ) {
                            Text(text = "System default")
                        }
                    }
                }
            }
        }

    }

}