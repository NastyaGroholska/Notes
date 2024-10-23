package com.ahrokholska.notes.presentation.screens.noteDetails.routine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.ChangeRoutineTasksSubNoteCheckUseCase
import com.ahrokholska.notes.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.notes.domain.useCase.FinishNoteUseCase
import com.ahrokholska.notes.domain.useCase.PinNoteUseCase
import com.ahrokholska.notes.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetRoutineTasksNoteDetailsUseCase
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
class RoutineTasksDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getRoutineTasksNoteDetailsUseCase: GetRoutineTasksNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
    private val changeRoutineTasksSubNoteCheckUseCase: ChangeRoutineTasksSubNoteCheckUseCase
) : NoteDetailsViewModel(
    savedStateHandle, NoteType.RoutineTasks, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase,
    finishNoteUseCase
) {
    val note = getRoutineTasksNoteDetailsUseCase(id)
        .map {
            it?.let {
                Note.RoutineTasks(
                    id = it.id,
                    active = it.active,
                    completed = it.completed,
                    isFinished = it.isFinished,
                    isPinned = it.isPinned,
                    color = noteColors[0]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun finishSubNote(subNoteId: Int) {
        if (isDeleting) return
        viewModelScope.launch {
            changeRoutineTasksSubNoteCheckUseCase(id, subNoteId, true)
        }
    }

    fun unFinishSubNote(subNoteId: Int) {
        if (isDeleting) return
        viewModelScope.launch {
            changeRoutineTasksSubNoteCheckUseCase(id, subNoteId, false)
        }
    }
}