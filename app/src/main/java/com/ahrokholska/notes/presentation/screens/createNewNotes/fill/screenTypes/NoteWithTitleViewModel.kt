package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class NoteWithTitleViewModel : ViewModel() {
    protected val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private var isSaving = false

    fun changeTitle(title: String) {
        viewModelScope.launch {
            _title.update { title }
        }
    }

    protected fun saveNote(block: suspend () -> Unit) {
        if (isSaving) return
        isSaving = true
        viewModelScope.launch {
            block()
        }
    }
}