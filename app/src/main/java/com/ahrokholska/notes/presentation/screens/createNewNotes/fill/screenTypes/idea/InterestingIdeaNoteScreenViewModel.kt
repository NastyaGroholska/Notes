package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.idea

import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestingIdeaNoteScreenViewModel @Inject constructor() : NoteWithTitleViewModel() {
    private val _body = MutableStateFlow("")
    val body = _body.asStateFlow()

    fun changeBody(body: String) {
        viewModelScope.launch {
            _body.update { body }
        }
    }
}