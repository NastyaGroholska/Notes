package com.ahrokholska.notes.presentation.screens.noteDetails.guidance

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.notes.domain.useCase.FinishNoteUseCase
import com.ahrokholska.notes.domain.useCase.PinNoteUseCase
import com.ahrokholska.notes.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetGuidanceNoteDetailsUseCase
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.screens.noteDetails.NoteDetailsViewModel
import com.ahrokholska.notes.presentation.theme.noteColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GuidanceDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getGuidanceNoteDetailsUseCase: GetGuidanceNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
) : NoteDetailsViewModel(
    savedStateHandle, NoteType.Guidance, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase,
    finishNoteUseCase
) {
    val note = getGuidanceNoteDetailsUseCase(id)
        .map {
            it?.let {
                Note.Guidance(
                    id = it.id,
                    title = it.title,
                    body = it.body,
                    image = it.image,
                    isFinished = it.isFinished,
                    isPinned = it.isPinned,
                    color = noteColors[0]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}