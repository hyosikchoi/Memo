package com.hyosik.android.memojetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyosik.android.memojetpackcompose.ui.theme.MemoJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoJetpackComposeTheme {

                /** 메세지 아이디 카운트 상태 */
                val clickCount: MutableState<Int> = remember {
                    mutableStateOf(0)
                }

                /** 전체 메세지 리스트 상태 */
                val messageList: SnapshotStateList<Message> = remember { mutableStateListOf() }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {

                        /** 1-1 onClicked 이벤트 발생  */
                        AddMessage(onClicked = {
                            clickCount.value += 1
                            val newMsg = Message(
                                id = clickCount.value,
                                content = "메세지 입니다 ${clickCount.value}"
                            )
                            /** 1-2 messageList 상태 변경 발생 */
                            messageList.add(newMsg)
                        })

                        /** 1-3 messageList 상태를 내려줍니다. */
                        /** 2-1 onDeleteClicked 이벤트 발생 */
                        /** 2-3 messageList 상태를 내려줍니다. */
                        MessageList(messages = messageList, onDeleteClicked = {
                            /** 2-2 messageList 상태 변경 발생 */
                            messageList.remove(it)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun AddMessage(onClicked: () -> Unit) {
    Button(onClick = onClicked) {
        Text(text = "메세지 추가")
    }
}

@Composable
fun MessageRow(msg: Message, onDeleteClicked: (Message) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color = Color.LightGray),
        elevation = 10.dp
    ) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "id : ${msg.id} / msg: ${msg.content}")
            Button(onClick = { onDeleteClicked(msg) }, modifier = Modifier.padding(top = 10.dp)) {
                Text(text = "삭제")
            }
        }
    }
}

@Composable
fun MessageList(messages: List<Message>, onDeleteClicked: (Message) -> Unit) {
    LazyColumn {
        items(messages) { message ->
            MessageRow(msg = message, onDeleteClicked = onDeleteClicked)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MemoJetpackComposeTheme {

        /** 전체 메세지 리스트 상태 */
        val messageList: SnapshotStateList<Message> = remember { mutableStateListOf<Message>().apply {
            add(
                Message(
                    id = 1,
                    content = "메세지 입니다"
                )
            )
        } }

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            MessageList(messages = messageList, onDeleteClicked = {
                messageList.remove(it)
            })
        }

    }
}