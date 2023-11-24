package com.starbet.ui.screens.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.starbet.R
import com.starbet.ui.screens.destinations.ChatDestination
import com.starbet.ui.theme.CardColor
import com.starbet.ui.theme.Primary
import com.starbet.ui.theme.TextDeep
import org.koin.androidx.compose.koinViewModel
import java.text.DateFormat

@Destination
@Composable
fun Conversations(navigator: DestinationsNavigator) {
    val viewModel: ChatViewModel = koinViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.getConversations()
    }
    val conversations = viewModel.conversations.observeAsState(listOf()).value

    LazyColumn {
        if (conversations.isEmpty()) {
            item {
                Text(
                    text = "No conversations", Modifier
                        .fillMaxWidth()
                        .padding(top=100.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
        items(
            items = conversations
        ) {
            Row(
                Modifier
                    .clickable {
                        navigator.navigate(ChatDestination(chatId = it.key, isAdmin = true))
                    }
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(CardColor, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.weight(0.9f)) {
                            Text(
                                text = it.name,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 4.dp),
                                color = TextDeep,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = it.lastMessage,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                color = TextDeep,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        IconButton(onClick = {
                            viewModel.deleteConversation(it.key).addOnSuccessListener {
//                            Toast.makeText(context, "Del", Toast.LENGTH_SHORT).show()
                                viewModel.getConversations()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Failed: ${it.localizedMessage}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = "delete",
                                tint = Primary
                            )
                        }
                    }
                    Text(
                        text = DateFormat.getDateTimeInstance().format(it.lastUpdated),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        textAlign = TextAlign.End,
                        color = TextDeep
                    )
                }
            }
        }
    }
}