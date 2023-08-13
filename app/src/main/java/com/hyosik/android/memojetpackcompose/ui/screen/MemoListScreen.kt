package com.hyosik.android.memojetpackcompose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.hyosik.android.memojetpackcompose.MainViewModel
import com.hyosik.android.memojetpackcompose.R
import com.hyosik.android.memojetpackcompose.ui.component.MessageList
import com.hyosik.android.memojetpackcompose.ui.theme.Pink200

@Composable
fun MemoScreen(
    viewModel: MainViewModel
) {

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

    val messageList by viewModel.messageList.collectAsState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.addMessage()
                },
                backgroundColor = Pink200,
                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp)
            ) {
//                            Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "")
                /** 위에서 정의한 LottieAnimation 삽입 */
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
            /** 1-3 messageList 상태를 내려줍니다. */

            /** 2-1 onDeleteClicked 이벤트 발생 */
            /** 2-3 messageList 상태를 내려줍니다. */
            /** 2-3 messageList 상태를 내려줍니다. */
            MessageList(messages = messageList, onDeleteClicked = {
                /** 2-2 messageList 상태 변경 발생 */
                /** 2-2 messageList 상태 변경 발생 */
                /** 2-2 messageList 상태 변경 발생 */

                /** 2-2 messageList 상태 변경 발생 */
                viewModel.removeMessage(it)
            },
                onCheckedChange = {
                    viewModel.updateIsLike(msgId = it.first, isLike = it.second)
                }
            )
        }
    }
}