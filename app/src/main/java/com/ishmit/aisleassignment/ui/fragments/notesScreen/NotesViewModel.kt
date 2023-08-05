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
    private val _notesResponse = MutableLiveData<ResponseRequest<NotesResponse>>()
    val notesResponse: LiveData<ResponseRequest<NotesResponse>> = _notesResponse

    fun getNotes(authToken: String) {
        viewModelScope.launch {
            _notesResponse.value = ResponseRequest.Loading
            val response = repository.getNotes(authToken)
            _notesResponse.value = response
        }
    }
}