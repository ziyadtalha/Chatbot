package com.example.test

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var conversationsList = mutableListOf<Conversation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadQuery("%")

        val recyclerView = findViewById<RecyclerView>(R.id.conversationRecycler)
        recyclerView.setBackgroundColor(Color.BLACK)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ConversationAdapter(conversationsList)

        val textBox = findViewById<EditText>(R.id.conversationNameToAdd)

        var textEntered:String

        val button: Button = findViewById(R.id.addConversation)

        button.setOnClickListener {
            textEntered = textBox.text.toString().trim()

            //making sure empty conversation not entered
            if (textEntered != "") {

                //make ConversationDbDAO object here
                val dbDAO = ConversationDbDAO(this)

                var conversation = Conversation(textEntered)

                val ID = dbDAO.insertRecord(conversation)

                if (ID>0){
                    //this is to avoid a visual bug
                    conversationsList.add(conversation)
                    Toast.makeText(this, "Conversation added!", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }

                textBox.text.clear()
                recyclerView.scrollToPosition(conversationsList.size - 1)

            }
            else {
                Toast.makeText(
                    this@MainActivity,
                    "Conversation Name can't be empty!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        openMessages()
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
        openMessages()
    }

    @SuppressLint("Range")
    private fun LoadQuery(name: String) {

        conversationsList.clear()

        //make ConversationDbDAO object here
        val dbDAO = ConversationDbDAO(this)

        conversationsList = dbDAO.loadQuery(name)

        //adapter
        var myConversationsAdapter = ConversationAdapter(conversationsList)

        //set adapter
        val recyclerView = findViewById<RecyclerView>(R.id.conversationRecycler)
        recyclerView.adapter = myConversationsAdapter

        recyclerView.scrollToPosition(conversationsList.size - 1)
    }

    private fun openMessages()
    {
        var adapter = ConversationAdapter(conversationsList)
        //set adapter
        val recyclerView = findViewById<RecyclerView>(R.id.conversationRecycler)
        recyclerView.adapter = adapter

        //when user clicks on a particular conversation to open the messages in it
        adapter.setOnItemClickListener(object :ConversationAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var index = position + 1
                Toast.makeText(this@MainActivity, "Conversation $index Opened!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, MessageActivity::class.java)

                val conversation = conversationsList[position].name

                //passing data to our new activity.
                intent.putExtra("conversation", conversation.toString())

                //starting a new activity.
                startActivity(intent)
            }

        })
    }
}