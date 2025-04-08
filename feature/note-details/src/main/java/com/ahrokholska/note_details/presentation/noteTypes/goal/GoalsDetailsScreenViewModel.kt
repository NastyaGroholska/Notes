package com.ahrokholska.note_details.presentation.noteTypes.goal

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.NoteType
import com.ahrokholska.note_details.domain.useCase.ChangeGoalsSubtaskCheckUseCase
import com.ahrokholska.note_details.domain.useCase.ChangeGoalsTaskCheckUseCase
import com.ahrokholska.note_details.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.note_details.domain.useCase.FinishNoteUseCase
import com.ahrokholska.note_details.domain.useCase.PinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.getNoteDetails.GetGoalsNoteDetailsUseCase
import com.ahrokholska.note_details.presentation.NoteDetailsViewModel
import com.ahrokholska.presentation_domain_mapper.toPresentation
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = GoalsDetailsScreenViewModel.Factory::class)
internal class GoalsDetailsScreenViewModel @AssistedInject constructor(
    @Assisted id: Int,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getGoalsNoteDetailsUseCase: GetGoalsNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
    private val changeGoalsTaskCheckUseCase: ChangeGoalsTaskCheckUseCase,
    private val changeGoalsSubtaskCheckUseCase: ChangeGoalsSubtaskCheckUseCase
) : NoteDetailsViewModel(
    id, NoteType.Goals, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase, finishNoteUseCase
) {
    val note = getGoalsNoteDetailsUseCase(id)
        .map { it?.toPresentation() }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun changeTaskCheck(checked: Boolean, index: Int) {
        if (isDeleting) return
        viewModelScope.launch {
            changeGoalsTaskCheckUseCase(id, index, checked)
        }
    }

    fun changeSubtaskCheck(checked: Boolean, taskIndex: Int, subtaskIndex: Int) {
        if (isDeleting) return
        viewModelScope.launch {
            changeGoalsSubtaskCheckUseCase(id, taskIndex, subtaskIndex, checked)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): GoalsDetailsScreenViewModel
    }
}