package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(private val messagesList: MutableList<Message>) : RecyclerView.Adapter<MyViewHolder>() {

    companion object {
        private const val RECEIVER_MESSAGE = 1
        private const val SENDER_MESSAGE = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = messagesList[position]
        if (message.name == "You") {
            return SENDER_MESSAGE
        }
        else {
            return RECEIVER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View
        if (viewType == RECEIVER_MESSAGE){
            listItem = layoutInflater.inflate(R.layout.receiver_message, parent, false)
        }
        else {
            listItem = layoutInflater.inflate(R.layout.sender_message, parent, false)
        }
        return  MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messagesList[position]
        if (message.name == "You"){
            holder.bindRight(message)
        }
        else {
            holder.bindLeft(message)
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

}

class MyViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bindLeft(messageText: Message){
        val userName = view.findViewById<TextView>(R.id.tvUserNameLeft)
        val userMessage = view.findViewById<TextView>(R.id.tvReceiverMessage)
        val userTimeStamp = view.findViewById<TextView>(R.id.tvReceiverTimeStamp)

        userName.text = messageText.name
        userMessage.text = messageText.content
        userTimeStamp.text = messageText.sentTime
    }

    fun bindRight(messageText: Message){
        val userName = view.findViewById<TextView>(R.id.tvUserNameRight)
        val userMessage = view.findViewById<TextView>(R.id.tvMessageRight)
        val userTimeStamp = view.findViewById<TextView>(R.id.tvSenderTimeStamp)

        userName.text = messageText.name
        userMessage.text = messageText.content
        userTimeStamp.text = messageText.sentTime
    }

}