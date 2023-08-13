package com.hyosik.android.memojetpackcompose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private var clickValue: Int = 1

    private val _messageList = MutableStateFlow<List<Message>>(emptyList())

    val messageList: StateFlow<List<Message>> get() = _messageList.asStateFlow()


    fun addMessage() {
        val beforeMessageList = _messageList.value.toMutableList()
        beforeMessageList.add(
            Message(
                id = clickValue,
                content = "메세지 입니다 $clickValue"
            )
        )
        _messageList.value = beforeMessageList.toList()
        clickValue += 1
    }

    fun removeMessage(message: Message) {
        val beforeMessageList = _messageList.value.toMutableList()
        beforeMessageList.remove(message)
        _messageList.value = beforeMessageList.toList()
    }

    fun updateIsLike(msgId: Int, isLike: Boolean) {
        _messageList.value = _messageList.value.map { msg: Message ->
            if(msg.id == msgId) msg.copy(isLike = isLike)
            else msg
        }
    }

}