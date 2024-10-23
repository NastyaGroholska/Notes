package com.ahrokholska.notes.presentation.screens.noteDetails.buy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.ChangeBuySomethingItemCheckUseCase
import com.ahrokholska.notes.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.notes.domain.useCase.FinishNoteUseCase
import com.ahrokholska.notes.domain.useCase.PinNoteUseCase
import com.ahrokholska.notes.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetBuySomethingNoteDetailsUseCase
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.screens.noteDetails.NoteDetailsViewModel
import com.ahrokholska.notes.presentation.theme.noteColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuySomethingDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getBuySomethingNoteDetailsUseCase: GetBuySomethingNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
    private val changeBuySomethingItemCheckUseCase: ChangeBuySomethingItemCheckUseCase
) : NoteDetailsViewModel(
    savedStateHandle, NoteType.BuyingSomething, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase,
    finishNoteUseCase
) {
    val note = getBuySomethingNoteDetailsUseCase(id)
        .map {
            it?.let {
                Note.BuyingSomething(
                    id = it.id,
                    title = it.title,
                    items = it.items,
                    isFinished = it.isFinished,
                    isPinned = it.isPinned,
                    color = noteColors[0]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun changeItemCheck(index: Int, checked: Boolean) {
        if (isDeleting) return
        viewModelScope.launch {
            changeBuySomethingItemCheckUseCase(id, index, checked)
        }
    }
}