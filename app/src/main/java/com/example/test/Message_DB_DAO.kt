package com.example.test
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class Message_DB_DAO(messageActivity: MessageActivity) {

    //THIS IS A COMMENT!
    private val context: Context = messageActivity.applicationContext

    var dbManager = MessageDbManager(context)

    fun insertRecord(message: Message) :Long {
        var values = ContentValues()
        values.put("name", message.name)
        values.put("content", message.content)
        values.put("conversation", message.conversation)

        //putting current time as well
        val timeFormat = SimpleDateFormat("h:mm a")
        val sentTime = timeFormat.format(Date())

        values.put("sentTime", sentTime.toString())

        val ID = dbManager.insert(values)

        return ID
    }

    @SuppressLint("Range")
    fun loadQuery(conversation: String): MutableList<Message>
    {
        var messagesList = mutableListOf<Message>()

        val projections = arrayOf("name", "content", "conversation", "sentTime")
        val selectionArgs = arrayOf(conversation)
        val cursor = dbManager.Query(projections, "conversation like ?", selectionArgs, "conversation")

        if (cursor.moveToFirst()) {
            do {
                //if I load ID, it gives error where app does not open once the first messages are inserted into DB
                //val loadedID = cursor.getInt(cursor.getColumnIndex("ID"))

                val loadedName = cursor.getString(cursor.getColumnIndex("name"))
                val loadedContent = cursor.getString(cursor.getColumnIndex("content"))
                val loadedConversation = cursor.getString(cursor.getColumnIndex("conversation"))
                val loadedSentTime = cursor.getString(cursor.getColumnIndex("sentTime"))

                var message = Message(loadedName, loadedContent, loadedConversation)

                //we need to set time like this because message is initialised with the current time by default
                message.setTime(loadedSentTime)

                //populate our list of messages
                messagesList.add(message)

            } while (cursor.moveToNext())
        }

        return messagesList
    }
}