package com.example.test

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class ConversationDbDAO(mainActivity: MainActivity) {
    private val context: Context = mainActivity.applicationContext

    var dbManager = ConversationDbManager(context)

    fun insertRecord(conversation: Conversation) :Long {
        var values = ContentValues()
        values.put("name", conversation.name)

        val ID = dbManager.insert(values)

        return ID
    }

    @SuppressLint("Range")
    fun loadQuery(name: String): MutableList<Conversation>
    {
        var conversationsList = mutableListOf<Conversation>()

        var dbManager = ConversationDbManager(context)
        val projections = arrayOf("name")
        val selectionArgs = arrayOf(name)
        val cursor = dbManager.Query(projections, "name like ?", selectionArgs, "name")


        if (cursor.moveToFirst()) {
            do {
                val loadedName = cursor.getString(cursor.getColumnIndex("name"))

                var conversation = Conversation(loadedName)

                //populate our list of Conversations
                conversationsList.add(conversation)

            } while (cursor.moveToNext())
        }

        return conversationsList
    }
}