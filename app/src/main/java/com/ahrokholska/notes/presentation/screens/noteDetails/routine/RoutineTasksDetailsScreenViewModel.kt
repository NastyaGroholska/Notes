package com.ahrokholska.notes.presentation.screens.noteDetails.routine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.ChangeRoutineTasksSubNoteCheckUseCase
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetRoutineTasksNoteDetailsUseCase
import com.ahrokholska.notes.presentation.model.Note
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
    getRoutineTasksNoteDetailsUseCase: GetRoutineTasksNoteDetailsUseCase,
    private val changeRoutineTasksSubNoteCheckUseCase: ChangeRoutineTasksSubNoteCheckUseCase
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    val note = getRoutineTasksNoteDetailsUseCase(id)
        .map {
            Note.RoutineTasks(
                id = it.id,
                active = it.active,
                completed = it.completed,
                isFinished = it.isFinished,
                isPinned = it.isPinned,
                color = noteColors[0]
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun finishSubNote(subNoteId: Int) {
        viewModelScope.launch {
            changeRoutineTasksSubNoteCheckUseCase(id, subNoteId, true)
        }
    }

    fun unFinishSubNote(subNoteId: Int) {
        viewModelScope.launch {
            changeRoutineTasksSubNoteCheckUseCase(id, subNoteId, false)
        }
    }
}