package com.hyosik.android.memojetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.google.accompanist.glide.rememberGlidePainter
import com.hyosik.android.memojetpackcompose.ui.detail.DetailActivity
import com.hyosik.android.memojetpackcompose.ui.theme.MemoJetpackComposeTheme
import com.hyosik.android.memojetpackcompose.ui.theme.Pink200

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoJetpackComposeTheme {

                val messageList by viewModel.messageList.collectAsState()

                /** Lottie Animation Resource 정의 */
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_add))
                val lottieAnimatable = rememberLottieAnimatable()

                /** CoroutineScope 이용한 lottie 시작 이팩트 정의 */
                LaunchedEffect(composition) {
                    lottieAnimatable.animate(
                        composition = composition,
                        clipSpec = LottieClipSpec.Frame(0, 1200),
                        initialProgress = 0f
                    )
                }

                // Android material design 과 흡사하다고 보면 된다.
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "메모 리스트") },
                            backgroundColor = Color.LightGray
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                viewModel.addMessage()
                            },
                            backgroundColor = Pink200,
                            modifier = Modifier.padding(end = 10.dp , bottom = 10.dp)
                            ) {
//                            Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "")
                            /** 위에서 정의한 LottieAnimation 삽입 */
                            LottieAnimation(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.size(75.dp)
                            )
                        }
                    }
                ) {
                    Column() {
                        /** 1-3 messageList 상태를 내려줍니다. */
                        /** 2-1 onDeleteClicked 이벤트 발생 */
                        /** 2-3 messageList 상태를 내려줍니다. */
                        MessageList(messages = messageList, onDeleteClicked = {
                            /** 2-2 messageList 상태 변경 발생 */
                            viewModel.removeMessage(it)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun MessageRow(msg: Message, onDeleteClicked: (Message) -> Unit) {

    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { navigateToDetailActivity(context,msg.id,msg.content) }
        ,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color = Color.LightGray),
        elevation = 10.dp
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadImageFromUrl(imageUrl = "https://fastly.picsum.photos/id/1048/200/200.jpg?hmac=W94UjOBeBuqxyKnyhht4a81ruXiXHpjQxvdZtNgGyow")
            Column(
                Modifier.padding(start = 10.dp)
            ) {
                Text(text = "id : ${msg.id}")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                Text(text = "msg: ${msg.content}")
                Button(
                    onClick = { onDeleteClicked(msg) },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(text = "삭제")
                }
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

@Composable
fun LoadImageFromUrl(imageUrl: String) {
    Image(
        painter = rememberGlidePainter(
            request = imageUrl,
            requestBuilder = {
                this.placeholder(R.drawable.ic_launcher_foreground)
                    .circleCrop()
            }
        ),
        contentDescription = "Image from $imageUrl",
        modifier = Modifier.size(100.dp)
    )
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MemoJetpackComposeTheme {
        /** 전체 메세지 리스트 상태 */
        val messageList: SnapshotStateList<Message> = remember {
            mutableStateListOf<Message>().apply {
                add(
                    Message(
                        id = 1,
                        content = "메세지 입니다"
                    )
                )
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "메모 리스트") },
                    backgroundColor = Color.LightGray
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Text(text = "클릭!!")
                }
            }
        ) {
            MessageList(messages = messageList, onDeleteClicked = {
                messageList.remove(it)
            })
        }
    }
}


fun navigateToDetailActivity(context: Context, id: Int, content: String) {
    val intent = Intent(context, DetailActivity::class.java)
    intent.putExtra("id",id)
    intent.putExtra("content", content)
    context.startActivity(intent)
}
