package com.ahrokholska.notes.presentation.screens.noteDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.notes.domain.useCase.FinishNoteUseCase
import com.ahrokholska.notes.domain.useCase.PinNoteUseCase
import com.ahrokholska.notes.domain.useCase.UnPinNoteUseCase
import kotlinx.coroutines.launch

open class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val type: NoteType,
    private val pinNoteUseCase: PinNoteUseCase,
    private val unPinNoteUseCase: UnPinNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val finishNoteUseCase: FinishNoteUseCase,
) : ViewModel() {
    protected val id = savedStateHandle.get<Int>("id") ?: 0
    protected var isDeleting = false

    fun deleteNote(onSuccess: () -> Unit) {
        if (isDeleting) return
        isDeleting = true
        viewModelScope.launch {
            deleteNoteUseCase(id, type)
            onSuccess()
        }
    }

    fun pinStatusChangeNote(isPinned: Boolean) {
        if (isDeleting) return
        viewModelScope.launch {
            if (isPinned) {
                unPinNoteUseCase(id, type)
            } else {
                pinNoteUseCase(id, type, System.currentTimeMillis())
            }
        }
    }

    fun finishNote() {
        if (isDeleting) return
        viewModelScope.launch {
            finishNoteUseCase(id, type, System.currentTimeMillis())
        }
    }
}