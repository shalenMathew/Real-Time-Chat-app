package com.example.chatapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.data.model.chat.ChatMessage

class ChatAdapter(
    private val messages: List<ChatMessage>,
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    inner class SentMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.tvMessageContent)
    }

    inner class ReceivedMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val senderName: TextView = view.findViewById(R.id.tvSenderName)
        val content: TextView = view.findViewById(R.id.tvMessageContent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender.id == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_SENT) {
            val view = inflater.inflate(R.layout.sender_item, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.reciver_item, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is SentMessageViewHolder) {
            holder.content.text = message.content ?: "[No content]"
        } else if (holder is ReceivedMessageViewHolder) {
            holder.senderName.text = "${message.sender.firstName} ${message.sender.lastName}"
            holder.content.text = message.content ?: "[No content]"
        }
    }

    override fun getItemCount(): Int = messages.size
}
