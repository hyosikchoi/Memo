package com.hyosik.android.memojetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.hyosik.android.memojetpackcompose.ui.component.MessageList
import com.hyosik.android.memojetpackcompose.ui.detail.DetailActivity
import com.hyosik.android.memojetpackcompose.ui.screen.ImportantMemoListScreen
import com.hyosik.android.memojetpackcompose.ui.screen.MemoScreen
import com.hyosik.android.memojetpackcompose.ui.theme.MemoJetpackComposeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val pages = listOf("메모", "중요")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoJetpackComposeTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val pagerState = rememberPagerState()
                    val coroutineScope = rememberCoroutineScope()

                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                            )
                        }
                    ) {
                        pages.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(text = title) },
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.scrollToPage(index)
                                    }
                                },

                            )
                        }
                    }

                    HorizontalPager(
                        count = pages.size,
                        state = pagerState
                    ) {page: Int ->
                        when(page) {
                            0 -> {
                                MemoScreen(viewModel = viewModel)
                            }
                            1 -> {
                                ImportantMemoListScreen()
                            }
                        }

                    }

                }
            }
        }
    }
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
    intent.putExtra("id", id)
    intent.putExtra("content", content)
    context.startActivity(intent)
}
