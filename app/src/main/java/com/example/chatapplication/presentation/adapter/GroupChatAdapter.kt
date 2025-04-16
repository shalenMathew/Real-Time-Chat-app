package com.example.chatapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.chatapplication.data.model.groupListModel.Data
import com.example.chatapplication.databinding.ItemGroupChatBinding

class GroupChatAdapter(private val onItemClick: (Data) -> Unit) : ListAdapter<Data, GroupChatAdapter.ChatViewHolder>(GroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemGroupChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }

   inner class ChatViewHolder(private val binding: ItemGroupChatBinding) :
      RecyclerView.ViewHolder(binding.root) {

        fun bind(group: Data) {
            binding.groupName.text = group.group.name
            binding.lastMessage.text = group.group.latestMessage.content
            binding.unreadCount.text = group.unreadCount.toString()

            binding.root.setOnClickListener {
                onItemClick(group)
            }

        }

    }

    class GroupDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.group._id == newItem.group._id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}
