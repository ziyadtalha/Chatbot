package com.example.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConversationAdapter(private val conversationsList: MutableList<Conversation>) : RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.conversation, parent, false)
        return  ConversationViewHolder(listItem, mListener)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversationsList[position]
        holder.bind(conversation)
    }

    override fun getItemCount(): Int {
        return conversationsList.size
    }


    class ConversationViewHolder(val view: View, listener: onItemClickListener):RecyclerView.ViewHolder(view){
        fun bind(conversationText: Conversation){
            val conversationName = view.findViewById<TextView>(R.id.conversationName)
            conversationName.text = conversationText.name
        }

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}