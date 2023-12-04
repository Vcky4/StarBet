package com.starbettipz.ui.screens.notification

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Notifications(navigator: DestinationsNavigator) {
//    val viewModel: AdminViewModel = koinViewModel()
//    val announcement = viewModel.announcement.observeAsState().value
//    var title by remember {
//        mutableStateOf(announcement?.title ?: "")
//    }
//    var body by remember {
//        mutableStateOf(announcement?.announcement ?: "")
//    }
//    val context = LocalContext.current
//    var processing by remember {
//        mutableStateOf(false)
//    }
//    var isAnnouncement by remember {
//        mutableStateOf(true)
//    }
//    LaunchedEffect(key1 = announcement) {
//        title = announcement?.title ?: ""
//        body = announcement?.announcement ?: ""
//    }
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Column(
//            Modifier
//                .background(Secondary, shape = MaterialTheme.shapes.medium)
//                .fillMaxWidth(0.9f)
////                .fillMaxHeight(0.7f)
//                .scrollable(
//                    orientation = Orientation.Vertical,
//                    state = rememberScrollState()
//                )
//                .padding(vertical = 50.dp, horizontal = 16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = stringResource(
//                    id =
//                    if (isAnnouncement) R.string.post_an_announcement
//                    else R.string.post_a_notification
//                ),
//                fontSize = 30.sp,
//                fontWeight = FontWeight.Bold,
//                color = TextDeep,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .clickable {
//                        isAnnouncement = !isAnnouncement
//                    }
//                    .fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = stringResource(id = R.string.please_enter_the_details),
//                fontSize = 18.sp,
//                color = Primary,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            TextField(
//                value = title,
//                onValueChange = { title = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.White,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent,
//                    errorIndicatorColor = Color.Transparent,
//                    cursorColor = Primary,
//                    textColor = TextDeep
//                ),
//                textStyle = TextStyle(
//                    fontSize = 28.sp,
//                    color = TextDeep,
//                    textAlign = TextAlign.Center,
//                ),
//                shape = RoundedCornerShape(8.dp),
//                placeholder = {
//                    Text(
//                        text = stringResource(id = R.string.type_it_here),
//                        fontSize = 28.sp,
//                        color = TextDeep.copy(alpha = 0.6f),
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                },
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            TextField(
//                value = body,
//                onValueChange = { body = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(min = 150.dp, max = 250.dp)
//                    .padding(horizontal = 16.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.White,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent,
//                    errorIndicatorColor = Color.Transparent,
//                    cursorColor = Primary,
//                    textColor = TextDeep
//                ),
//                textStyle = TextStyle(
//                    fontSize = 18.sp,
//                    color = TextDeep,
//                    textAlign = TextAlign.Center,
//                ),
//                shape = RoundedCornerShape(8.dp),
//                placeholder = {
//                    Text(
//                        text = stringResource(id = R.string.type_it_here),
//                        fontSize = 18.sp,
//                        color = TextDeep.copy(alpha = 0.6f),
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                },
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//            val path = stringResource(id = R.string.announcement)
//            Button(
//                onClick = {
//                    processing = true
//                    if (isAnnouncement) {
//                        viewModel.setAnnouncement(title, body, path)
//                            .addOnSuccessListener {
//                                Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show()
//                                processing = false
//                                navigator.popBackStack()
//                            }.addOnFailureListener {
//                                Toast.makeText(
//                                    context,
//                                    "Failed: ${it.localizedMessage}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                processing = false
//                            }
//                    } else {
//                        viewModel.sendNotification(title, body)
//                        Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show()
//                        processing = false
//                        navigator.popBackStack()
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = TextDeep,
//                    contentColor = Color.White
//                ),
//                modifier = Modifier.padding(horizontal = 26.dp)
//            ) {
//                if (!processing) {
//                    Text(
//                        text = stringResource(id = R.string.post),
//                        fontSize = 18.sp,
//                        color = Color.White,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                } else {
//                    CircularProgressIndicator(color = Color.White)
//                }
//            }
//        }
//    }

}
