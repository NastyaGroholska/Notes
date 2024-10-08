package com.ahrokholska.notes.presentation.screens.noteDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.PinNoteUseCase
import com.ahrokholska.notes.domain.useCase.UnPinNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NoteDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pinNoteUseCase: PinNoteUseCase,
    private val unPinNoteUseCase: UnPinNoteUseCase
) : ViewModel() {
    protected val id = savedStateHandle.get<Int>("id") ?: 0

    protected fun pinStatusChangeNote(isPinned: Boolean, type: NoteType) {
        viewModelScope.launch {
            if (isPinned) {
                unPinNoteUseCase(id, type)
            } else {
                pinNoteUseCase(id, type, System.currentTimeMillis())
            }
        }
    }
}