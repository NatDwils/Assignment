package com.ishmit.aisleassignment.ui.fragments.notesScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishmit.aisleassignment.data.models.NotesResponse
import com.ishmit.aisleassignment.data.remote.repository.UserRepository
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel(private val repository: UserRepository) : ViewModel() {
    // MutableLiveData to hold the response state
    private val _notesResponse = MutableLiveData<ResponseRequest<NotesResponse>>()

    // Exposing a LiveData for the UI to observe
    val notesResponse: LiveData<ResponseRequest<NotesResponse>> = _notesResponse

    // Function to fetch notes using the repository
    fun getNotes(authToken: String) {
        viewModelScope.launch {
            // Set the initial loading state
            _notesResponse.value = ResponseRequest.Loading

            // Call the repository function to get notes
            val response = repository.getNotes(authToken)

            // Update the LiveData with the response
            _notesResponse.value = response
        }
    }
}
