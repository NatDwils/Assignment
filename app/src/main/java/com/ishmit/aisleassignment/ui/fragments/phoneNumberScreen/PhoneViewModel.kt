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

    private val _phoneNumberResponse = MutableLiveData<ResponseRequest<PhoneNumberResponse>>()
    val phoneNumberResponse: LiveData<ResponseRequest<PhoneNumberResponse>> = _phoneNumberResponse

    fun phoneNumberLogin(number: String) {
        viewModelScope.launch {
            _phoneNumberResponse.value = ResponseRequest.Loading
            val response = repository.phoneNumberLogin(number)
            _phoneNumberResponse.value = response
        }
    }

}
