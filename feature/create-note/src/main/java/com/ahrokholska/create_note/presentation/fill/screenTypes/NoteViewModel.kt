package com.ahrokholska.create_note.presentation.fill.screenTypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

internal open class NoteViewModel : ViewModel() {
    protected var isSaving = false
        private set

    protected fun saveNote(block: suspend () -> Unit) {
        if (isSaving) return
        isSaving = true
        viewModelScope.launch {
            block()
            isSaving = false
        }
    }
}