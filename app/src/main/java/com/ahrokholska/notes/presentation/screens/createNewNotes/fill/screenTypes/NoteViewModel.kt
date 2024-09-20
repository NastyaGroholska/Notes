package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class NoteViewModel : ViewModel() {
    private var isSaving = false

    protected fun saveNote(block: suspend () -> Unit) {
        if (isSaving) return
        isSaving = true
        viewModelScope.launch {
            block()
        }
    }
}