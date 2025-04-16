package com.example.chatapplication.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.data.model.chat.ChatMessage
import com.example.chatapplication.databinding.FragmentChatBinding
import com.example.chatapplication.presentation.adapter.ChatAdapter
import com.example.chatapplication.presentation.socket.SocketManager
import com.example.chatapplication.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.chatapplication.utils.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var groupId: String? = null
    private var authToken: String? = null
    private var id: String? = null

    private val messages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            groupId = it.getString("groupId")
            authToken = it.getString("authToken")
            id = it.getString("id")
        }

        Log.d("TAG", "Arguments received: groupId=$groupId, authToken=${authToken?.take(10)}, id=$id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        Log.d("TAG", "FragmentChatBinding inflated")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.sendMessageResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.sendButton.isEnabled = false
                    Log.d("TAG", "Sending message... Loading")
                }
                is Result.Success -> {
                    binding.sendButton.isEnabled = true
                    binding.messageEditText.text.clear()
                    Log.d("TAG", "Message sent successfully: ${result.data}")
                }
                is Result.Error -> {
                    binding.sendButton.isEnabled = true
                    Log.e("TAG", "Error sending message: ${result.message}")
                }
            }
        })

        binding.sendButton.setOnClickListener {
            Log.d("TAG", "Send button clicked")
            sendMessage()
        }

        groupId?.let { groupId ->
            authToken?.let { token ->
                try {
                    SocketManager.initSocket(token)
                    SocketManager.connect()
                    Log.d("TAG", "Socket initialized and connected")
                } catch (e: Exception) {
                    Log.e("TAG", "Error initializing socket: ${e.message}")
                }
            } ?: Log.e("TAG", "Auth token is null. Cannot initialize socket.")
        } ?: Log.e("TAG", "Group ID is null. Cannot initialize socket.")
    }

    private fun sendMessage() {
        val content = binding.messageEditText.text.toString().trim()

        // Check if content is not empty
        if (content.isEmpty()) {
            Log.w("TAG", "Message content is empty, not sending")
            return
        }

        // Create a dummy file with message content
        val file: MultipartBody.Part = createDummyFile(content)

        // Send message to the backend
        groupId?.let { groupId ->
            authToken?.let { token ->
                Log.d("TAG", "Sending message: \"$content\" to group: $groupId")
                viewModel.sendMessage(token, groupId, content, file)
            } ?: Log.e("TAG", "Auth token is null, cannot send message")
        } ?: Log.e("TAG", "Group ID is null, cannot send message")
    }

    private fun createDummyFile(message: String): MultipartBody.Part {
        // Create a temporary file with the text content
        val tempFile = File.createTempFile("message_", ".txt", requireContext().cacheDir)
        tempFile.writeText(message)

        // Convert file to MultipartBody.Part for sending
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), tempFile)
        return MultipartBody.Part.createFormData("file", tempFile.name, requestBody)
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(messages, currentUserId = id ?: "")
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }
        Log.d("TAG", "RecyclerView setup complete")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "ChatFragment view destroyed")
        _binding = null
    }
}
