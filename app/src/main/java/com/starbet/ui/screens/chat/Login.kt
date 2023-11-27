package com.starbet.ui.screens.chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starbet.R
import com.starbet.ui.theme.CardColor
import com.starbet.ui.theme.Primary
import com.starbet.ui.theme.Secondary
import com.starbet.ui.theme.TextDeep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(viewModel: ChatViewModel) {
    var pin by remember {
        mutableStateOf("")
    }
    var processing by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .background(CardColor, shape = MaterialTheme.shapes.medium)
                .fillMaxWidth(0.9f)
                .padding(vertical = 50.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.authenticate),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextDeep,
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
                    cursorColor = Primary,
                    textColor = TextDeep
                ),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_your_pin),
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
                    viewModel.getPassword.addOnSuccessListener {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        processing = false
                        Log.d("TAG", "Login: ${it.value}")
                        if (it.value == pin) {
                            viewModel.login("ftyujvf")
                        } else {
                            Toast.makeText(
                                context,
                                "Wrong pin, please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Failed: ${it.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                        processing = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TextDeep,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(horizontal = 26.dp),
                enabled = pin.length > 2 && !processing
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
}