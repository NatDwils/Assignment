package com.ishmit.aisleassignment.ui.fragments.phoneNumberScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishmit.aisleassignment.data.models.PhoneNumberResponse
import com.ishmit.aisleassignment.data.remote.repository.UserRepository
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhoneViewModel(private val repository: UserRepository) : ViewModel() {

    // MutableLiveData to hold the response state of phone number login
    private val _phoneNumberResponse = MutableLiveData<ResponseRequest<PhoneNumberResponse>>()

    // LiveData to expose the response state to the UI
    val phoneNumberResponse: LiveData<ResponseRequest<PhoneNumberResponse>> = _phoneNumberResponse

    // Function to initiate phone number login
    fun phoneNumberLogin(number: String) {
        viewModelScope.launch {
            // Set loading state
            _phoneNumberResponse.value = ResponseRequest.Loading
            // Call repository's phoneNumberLogin function
            val response = repository.phoneNumberLogin(number)
            // Set the response state based on the result
            _phoneNumberResponse.value = response
        }
    }
}
