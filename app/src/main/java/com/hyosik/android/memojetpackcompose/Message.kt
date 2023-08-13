package com.hyosik.android.memojetpackcompose

data class Message(
    val id: Int,
    val content: String,
    val isLike: Boolean = false
)
