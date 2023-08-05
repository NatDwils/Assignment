package com.ishmit.aisleassignment.ui.fragments.OtpScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishmit.aisleassignment.data.models.OtpResponse
import com.ishmit.aisleassignment.data.remote.repository.UserRepository
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class OtpViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    // LiveData to hold OTP verification response
    private val _otpResponse = MutableLiveData<ResponseRequest<OtpResponse>>()
    val otpResponse: LiveData<ResponseRequest<OtpResponse>> = _otpResponse

    // LiveData to hold the formatted timer value
    private val _timer = MutableLiveData<String>()
    val timer: LiveData<String> = _timer

    // Job to manage the OTP timer coroutine
    private var otpTimerJob: Job? = null

    // Function to verify OTP
    fun verifyOtp(number: String, otp: String) {
        viewModelScope.launch {
            _otpResponse.value = ResponseRequest.Loading
            val response = repository.verifyOtp(number, otp)
            _otpResponse.value = response
        }
    }

    // Function to initiate OTP resend and start the timer
    fun resendOtp() {
        startOtpTimer(60)
    }

    // Function to start the OTP timer
    fun startOtpTimer(durationInSeconds: Int) {
        otpTimerJob?.cancel()
        otpTimerJob = viewModelScope.launch {
            var seconds = durationInSeconds
            while (seconds >= 0) {
                _timer.value = getTimerFormattedString(seconds)
                delay(1000)
                seconds--
            }
        }
    }

    // Function to format timer value into minutes and seconds
    private fun getTimerFormattedString(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds)
    }
}
