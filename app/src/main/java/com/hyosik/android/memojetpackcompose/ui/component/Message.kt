package com.hyosik.android.memojetpackcompose.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import com.hyosik.android.memojetpackcompose.Message
import com.hyosik.android.memojetpackcompose.R
import com.hyosik.android.memojetpackcompose.navigateToDetailActivity


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageList(
    messages: List<Message>,
    onDeleteClicked: (Message) -> Unit,
    onCheckedChange: (Pair<Int, Boolean>) -> Unit
) {
    LazyColumn {
        // 0 ~ 9 = 0 , 10 ~ 19 = 1
        val groupedItems = messages.groupBy { it.id / 10 }

        groupedItems.forEach { (title, messageList) ->

            stickyHeader {
                Surface(Modifier.fillParentMaxWidth()) {
                    Text(
                        text = "id ( ${messageList.first().id} ~ ${messageList.last().id} )",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                }
            }

            items(
                messageList
            ) { message ->
                MessageRow(
                    msg = message,
                    onDeleteClicked = onDeleteClicked,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

@Composable
fun MessageRow(
    msg: Message,
    onDeleteClicked: (Message) -> Unit,
    onCheckedChange: (Pair<Int, Boolean>) -> Unit
) {

    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { navigateToDetailActivity(context, msg.id, msg.content) },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color = Color.LightGray),
        elevation = 10.dp
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
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
            CheckBoxWithImg(msg, onCheckedChange)
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

@Composable
fun CheckBoxWithImg(msg: Message, onCheckedChange: (Pair<Int, Boolean>) -> Unit) {

    val imageResource = if (msg.isLike) {
        painterResource(id = R.drawable.ic_checked)
    } else {
        painterResource(id = R.drawable.ic_unchecked)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Image(
            painter = imageResource,
            contentDescription = null, // Content description for accessibility
            modifier = Modifier
                .size(24.dp)
                .clickable { onCheckedChange(Pair(msg.id, msg.isLike.not())) },
            contentScale = ContentScale.Fit
        )
    }
}