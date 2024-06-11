package com.example.talktrove.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talktrove.R
import com.example.talktrove.data.MessageModel
import com.example.talktrove.ui.theme.Purple40
import com.example.talktrove.ui.theme.Purple80


@Composable
fun ChatPage(modifier: Modifier, viewModel: ChatViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.Black)) {

        appHeader()
        MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })

    }
}


@Composable
fun appHeader() {


    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
        text = "TalkTrove",
        fontSize = 19.sp, fontWeight = FontWeight.SemiBold
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        value = message,
        onValueChange = { message = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(text = "Enter your message") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.Gray,
            focusedBorderColor = Color.White
        ),
        trailingIcon = {
            IconButton(onClick = {
                if (message.isNotEmpty()) {
                    onMessageSend(message)
                    message = ""
                }

            }) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        },
        shape = RoundedCornerShape(30.dp),

        )
}


@Composable
fun MessageList(modifier: Modifier, messageList: List<MessageModel>) {

    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(200.dp).alpha(.7f),
                painter = painterResource(id = R.drawable.question_answer_24),
                contentDescription = "Center Image",
                tint = Purple80,
            )
            Text(
                text = "Start Chat by Sending Message!",
                color = Color.White.copy(alpha = .5f),
                fontWeight = FontWeight.W400
            )

        }
    } else {

        LazyColumn(modifier = modifier, reverseLayout = true) {

            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }


}


@Composable
fun MessageRow(messageModel: MessageModel) {

    val isModel = messageModel.role == "model"
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 18.dp else 70.dp,
                        end = if (isModel) 70.dp else 18.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(35.dp))
                    .background(if (isModel) Color.Transparent else Color.White.copy(alpha = .1f))
                    .padding(16.dp),

                )


            {
                SelectionContainer {

                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W400,
                        color = Color.White
                    )
                }
            }

        }

    }

}


