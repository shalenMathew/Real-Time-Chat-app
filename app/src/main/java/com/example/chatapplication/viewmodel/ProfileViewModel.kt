package com.example.chatapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.model.profileModel.responsebody.ProfileResponse
import com.example.chatapplication.data.repository.ProfileRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.chatapplication.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileUpdateState = MutableLiveData<Result<ProfileResponse>>()
    val profileUpdateState: LiveData<Result<ProfileResponse>> = _profileUpdateState


 fun updateUserProfile(authToken: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            profileRepository.updateUserProfile(authToken, firstName, lastName).collect { result->
              _profileUpdateState.value = result
            }

        }
    }

}
