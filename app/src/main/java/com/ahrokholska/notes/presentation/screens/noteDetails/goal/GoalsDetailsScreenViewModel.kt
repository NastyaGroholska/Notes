package com.ahrokholska.notes.presentation.screens.noteDetails.goal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.ChangeGoalsSubtaskCheckUseCase
import com.ahrokholska.notes.domain.useCase.ChangeGoalsTaskCheckUseCase
import com.ahrokholska.notes.domain.useCase.PinNoteUseCase
import com.ahrokholska.notes.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetGoalsNoteDetailsUseCase
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
class GoalsDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getGoalsNoteDetailsUseCase: GetGoalsNoteDetailsUseCase,
    private val changeGoalsTaskCheckUseCase: ChangeGoalsTaskCheckUseCase,
    private val changeGoalsSubtaskCheckUseCase: ChangeGoalsSubtaskCheckUseCase
) : NoteDetailsViewModel(savedStateHandle, pinNoteUseCase, unPinNoteUseCase) {
    val note = getGoalsNoteDetailsUseCase(id)
        .map {
            Note.Goals(
                id = it.id,
                title = it.title,
                tasks = it.tasks,
                isFinished = it.isFinished,
                isPinned = it.isPinned,
                color = noteColors[0]
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun changeTaskCheck(checked: Boolean, index: Int) {
        viewModelScope.launch {
            changeGoalsTaskCheckUseCase(id, index, checked)
        }
    }

    fun changeSubtaskCheck(checked: Boolean, taskIndex: Int, subtaskIndex: Int) {
        viewModelScope.launch {
            changeGoalsSubtaskCheckUseCase(id, taskIndex, subtaskIndex, checked)
        }
    }

    fun pinStatusChangeNote(isPinned: Boolean) = pinStatusChangeNote(isPinned, NoteType.Goals)
}