package com.starbet.ui.screens.chat

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.ktx.messaging
import com.starbet.R
import com.starbet.ui.theme.CardColor2
import com.starbet.ui.theme.Primary
import com.starbet.ui.theme.Secondary
import com.starbet.ui.theme.TextDeep
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Chat(
    chatId: String = "", isAdmin: Boolean = false, navigator: DestinationsNavigator
) {
    val viewModel: ChatViewModel = koinViewModel()
    var processing by remember {
        mutableStateOf(false)
    }
    var uploadLoading by remember {
        mutableStateOf(false)
    }
    var name by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }
    val listState = rememberLazyListState()

    val userName = viewModel.userName.observeAsState("").value
    val cId = chatId.ifEmpty { viewModel.chatId.observeAsState("").value }
    val chats = viewModel.chats.observeAsState(listOf()).value
    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uploadLoading = true
        imageUri = uri
        viewModel.uploadImage(
            message,
            if (isAdmin) "Admin" else userName,
            isAdmin = isAdmin,
            parent = cId,
            uri = imageUri.toString()
        ).addOnSuccessListener {
//                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            viewModel.getChats(cId)
            message = ""
            uploadLoading = false
        }.addOnFailureListener {
            Toast.makeText(
                context, "Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT
            ).show()
            uploadLoading = false
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getChats(cId)
    }
    //scroll to bottom when new item added
    LaunchedEffect(key1 = chats) {
        //check if list is empty
        if (chats.isNotEmpty()) {
            listState.animateScrollToItem(chats.size - 1)
        }
    }


    LaunchedEffect(key1 = Unit) {
        if (isAdmin) {
            Firebase.messaging.subscribeToTopic("adminChat").addOnCompleteListener { task ->
                var msg = "Subscribed adminChat"
                if (!task.isSuccessful) {
                    msg = "Subscribe adminChat failed"
                }
                Log.d(Constants.TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        } else {
            Firebase.messaging.subscribeToTopic(cId).addOnCompleteListener { task ->
                var msg = "Subscribed $cId"
                if (!task.isSuccessful) {
                    msg = "Subscribe $cId failed"
                }
                Log.d(Constants.TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (userName.isEmpty() && !isAdmin) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                Modifier
                    .background(Secondary, shape = MaterialTheme.shapes.medium)
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 50.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.contact_us),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDeep,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.whats_your_name),
                    fontSize = 18.sp,
                    color = Primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        cursorColor = Primary,
                        textColor = TextDeep
                    ),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_your_name),
                            fontSize = 18.sp,
                            color = TextDeep.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        viewModel.setUserName(name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TextDeep, contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 26.dp),
                    enabled = name.length > 2 && !processing
                ) {
                    if (!processing) {
                        Text(
                            text = stringResource(id = R.string.access),
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }
        }
    } else {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier.weight(1f), state = listState
            ) {
                items(
                    items = chats,
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = if (isAdmin) {
                            if (it.admin) Arrangement.End else Arrangement.Start
                        } else {
                            if (it.admin) Arrangement.Start else Arrangement.End
                        }
                    ) {
                        Card(
                            modifier = Modifier
                                .widthIn(min = 70.dp, max = 260.dp)
                                .padding(top = 6.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isAdmin) {
                                    if (it.admin) CardColor2.copy(0.7f)
                                    else Secondary.copy(0.7f)
                                } else {
                                    if (it.admin) Secondary.copy(0.7f)
                                    else CardColor2.copy(0.7f)
                                },
                            ),
                        ) {
                            Column {
                                if (isAdmin && it.admin) {
                                    Icon(painter = painterResource(id = R.drawable.delete),
                                        contentDescription = "delete",
                                        tint = Primary,
                                        modifier = Modifier.clickable {
                                            viewModel.deleteChat(it.parent, it.key)
                                                .addOnSuccessListener {
                                                    viewModel.getChats(cId)
                                                }.addOnFailureListener {
                                                    Toast.makeText(
                                                        context,
                                                        "Failed: ${it.localizedMessage}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                        })

                                }
                                //show image if available
                                if (it.imageUrl.isNotEmpty()) {
                                    Box {
                                        AsyncImage(
                                            model = it.imageUrl,
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            contentScale = ContentScale.FillBounds
                                        )
                                        if (uploadLoading) {
                                            CircularProgressIndicator(
                                                color = Primary,
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = it.message,
                                    fontSize = 18.sp,
                                    color = TextDeep,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.padding(
                                        start = 16.dp, bottom = 8.dp, end = 16.dp, top = 12.dp
                                    )
                                )
                                Text(
                                    text = getTime(it.time), modifier = Modifier
                                        .align(
                                            if (it.admin && !isAdmin) Alignment.Start else Alignment.End
                                        )
                                        .padding(
                                            start = 16.dp, end = 16.dp, bottom = 12.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }
            Row(
                Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .heightIn(max = 150.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
//                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                AnimatedVisibility(visible = message.isEmpty()) {
                IconButton(enabled = !processing, onClick = {
                    launcher.launch("image/*")
//                        viewModel.sendChat(
//                            message,
//                            if (isAdmin) "Admin" else userName,
//                            isAdmin = isAdmin,
//                            parent = cId
//                        ).addOnSuccessListener {
////                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
//                            viewModel.getChats(cId)
//                            message = ""
//                            processing = false
//                        }.addOnFailureListener {
//                            Toast.makeText(
//                                context, "Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT
//                            ).show()
//                            processing = false
//                        }
                }) {
//                        if (processing) {
//                            CircularProgressIndicator(
//                                color = Primary
//                            )
//                        } else {
                    Icon(
                        painter = painterResource(id = R.drawable.add_photo),
                        contentDescription = "Send",
                        tint = Primary,
//                        modifier = Modifier.size(24.dp)
                    )
//                        }
                }
//                }
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        cursorColor = Primary,
                        textColor = TextDeep
                    ),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.start_typing),
                            fontSize = 18.sp,
                            color = TextDeep.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )

                AnimatedVisibility(visible = message.isNotEmpty()) {
                    IconButton(enabled = !processing, onClick = {
                        viewModel.sendChat(
                            message,
                            if (isAdmin) "Admin" else userName,
                            isAdmin = isAdmin,
                            parent = cId
                        ).addOnSuccessListener {
//                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                            viewModel.getChats(cId)
                            message = ""
                            processing = false
                        }.addOnFailureListener {
                            Toast.makeText(
                                context, "Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT
                            ).show()
                            processing = false
                        }
                    }) {
                        if (processing) {
                            CircularProgressIndicator(
                                color = Primary
                            )
                        } else if (!uploadLoading) {
                            Icon(
                                painter = painterResource(id = R.drawable.send),
                                contentDescription = "Send",
                                tint = Primary,
                            )
                        }
                    }
                }
            }
        }
    }
}

//convert long o time
fun getTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return format.format(date)
}