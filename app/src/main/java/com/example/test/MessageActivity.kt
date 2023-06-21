package com.example.test

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MessageActivity : AppCompatActivity() {

    private var messagesList = mutableListOf<Message>()
    private lateinit var conversation: String
    private  val replyMessages = arrayOf<String>("Ok!", "Done!", "Wow!", "Interesting!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages)

        //Load from DB
        conversation = intent.getStringExtra("conversation").toString()
        LoadMessages(conversation)

        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)
        recyclerView.setBackgroundColor(Color.BLACK)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val textBox = findViewById<EditText>(R.id.myTextBox)

        val sendButton = findViewById<Button>(R.id.mySendButton)

        var textEntered = ""

        sendButton.setOnClickListener {
            textEntered = textBox.text.toString().trim()

            //making sure empty message not entered
            if (textEntered != "")
            {
                val userMessage = Message("You", textEntered, conversation)

                messagesList.add(userMessage)

                val dbDAO = Message_DB_DAO(this)

                var ID = dbDAO.insertRecord(userMessage)

                if (ID>0)
                {
                    Toast.makeText(this, "Message posted!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }

                //let's select a random message from our replyMessages array
                val randomIndex = Random.nextInt(0, replyMessages.size)
                val reply = replyMessages[randomIndex]

                val senderMessage = Message(conversation, reply, conversation)

                messagesList.add(senderMessage)

                ID = dbDAO.insertRecord(senderMessage)

                if (ID > 0)
                {
                    Toast.makeText(this, "Message posted!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }

                textBox.text.clear()
                recyclerView.scrollToPosition(messagesList.size - 1)
            }

            else
            {
                Toast.makeText(
                    this@MessageActivity,
                    "Please enter a message!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        LoadMessages(conversation)
    }

    private fun LoadMessages(conversation: String) {
        messagesList.clear()

        val dbDAO = Message_DB_DAO(this)

        messagesList = dbDAO.loadQuery(conversation)

        //adapter
        var myMessagesAdapter = MyRecyclerViewAdapter(messagesList)

        //set adapter
        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)
        recyclerView.adapter = myMessagesAdapter
        recyclerView.scrollToPosition(messagesList.size - 1)
    }
}