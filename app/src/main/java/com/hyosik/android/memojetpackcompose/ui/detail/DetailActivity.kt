package com.hyosik.android.memojetpackcompose.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailActivity : ComponentActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val detailId: Int = intent.getIntExtra("id", 0)
        val detailContent: String = intent.getStringExtra("content") ?: ""

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "메모 디테일") },
                        backgroundColor = Color.LightGray
                    )
                }
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Text(text = "id : $detailId" , fontSize = 20.sp , fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(top = 25.dp))
                        Text(text = "content : $detailContent" , fontSize = 20.sp , fontWeight = FontWeight.Bold)
                    }
                }

            }

        }
    }

}