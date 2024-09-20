package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class NoteWithTitleViewModel : NoteViewModel() {
    protected val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    fun changeTitle(title: String) {
        viewModelScope.launch {
            _title.update { title }
        }
    }
}