package com.example.test

import java.text.SimpleDateFormat
import java.util.*

class Message
    (
    val name: String?,
    val content: String,
    val conversation: String?
    )
{
    var sentTime: String

    fun setTime(time: String)
    {
        this.sentTime = time
    }
    init
    {
        val timeFormat = SimpleDateFormat("h:mm a")
        this.sentTime = timeFormat.format(Date())
    }
}