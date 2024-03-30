package com.betstrivepro.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.betstrivepro.R
import com.betstrivepro.ui.screens.destinations.ChatDestination
import com.betstrivepro.ui.theme.CardColor
import com.betstrivepro.ui.theme.TextDeep
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun VipPin(trigger: String, navigator: DestinationsNavigator) {
    var pin by remember {
        mutableStateOf("")
    }
    var isInvalid by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isInvalid) {
        delay(4000)
        isInvalid = false
    }
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navigator.navigateUp() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.White
                )
            }
            Text(
                text = trigger,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 16.dp),
            )
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                Modifier
                    .background(CardColor, shape = shapes.medium)
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 30.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.input_your_vip_pin),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = pin,
                    onValueChange = { pin = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        textColor = TextDeep
                    ),
                    textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_your_vip_pin),
                            fontSize = 18.sp,
                            color = TextDeep.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (isInvalid) {
                    Text(text = stringResource(id = R.string.contact_admin_for), color = TextDeep)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { isInvalid = !isInvalid },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TextDeep,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 26.dp),
                    enabled = pin.length > 2
                ) {
                    Text(
                        text = stringResource(id = R.string.access),
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.clickable {
//                            context.openWhatsApp(whatsapp)
//                        }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.whatsapp),
//                            contentDescription = "whatsapp",
//                            tint = TextDeep
//                        )
//                        Text(
//                            text = "Whatsapp",
//                            fontSize = 18.sp,
//                            color = TextDeep,
//                            textAlign = TextAlign.Center,
//                        )
//                    }
                    val appName = stringResource(id = R.string.app_name)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            navigator.navigate(ChatDestination())
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.chat),
                            contentDescription = stringResource(id = R.string.chat_with_us),
                            tint = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.chat_with_us),
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.clickable {
//                            context.openTelegram(telegram)
//                        }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.telegram),
//                            contentDescription = "telegram",
//                            tint = TextDeep
//                        )
//                        Text(
//                            text = "Telegram",
//                            fontSize = 18.sp,
//                            color = TextDeep,
//                            textAlign = TextAlign.Center,
//                        )
//                    }


                }
            }
        }
    }
}