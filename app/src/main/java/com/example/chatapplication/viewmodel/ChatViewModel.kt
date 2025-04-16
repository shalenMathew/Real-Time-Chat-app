package com.example.chatapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.model.groupListModel.Data
import com.example.chatapplication.data.repository.ChatRepository
import com.example.chatapplication.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _groupList = MutableLiveData<Result<List<Data>>>()
    val groupList: LiveData<Result<List<Data>>> = _groupList

    private val _sendMessageResult = MutableLiveData<Result<String>>()
    val sendMessageResult: LiveData<Result<String>> = _sendMessageResult



    fun fetchGroups(authToken: String) {

        _groupList.value = Result.Loading()

        viewModelScope.launch {
            // Make the network call to fetch groups
            val result = chatRepository.fetchGroups(authToken)

            // Post the result to LiveData
            _groupList.value = result
        }
    }


    fun sendMessage(
        token: String,
        groupId: String,
        content: String?,
        file: MultipartBody.Part?
    ) {
        _sendMessageResult.value = Result.Loading()

        viewModelScope.launch {
            val result = chatRepository.sendMessage(token, groupId, content, file)
            _sendMessageResult.value = result
        }
    }


}
