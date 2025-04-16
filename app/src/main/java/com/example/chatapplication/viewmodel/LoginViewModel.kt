package com.example.chatapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.model.loginModel.response.LoginResponse
import com.example.chatapplication.data.repository.LoginRepository
import com.example.chatapplication.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository // Provided by Hilt
) : ViewModel() {


    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult


    fun loginUser(mobile: Long) {

        viewModelScope.launch {
            repository.loginUser(mobile).collect { result ->
                _loginResult.value = result
            }
        }
    }
}
