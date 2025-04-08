package com.ahrokholska.note_details.presentation.noteTypes.routine

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.NoteType
import com.ahrokholska.note_details.domain.useCase.ChangeRoutineTasksSubNoteCheckUseCase
import com.ahrokholska.note_details.domain.useCase.DeleteNoteUseCase
import com.ahrokholska.note_details.domain.useCase.FinishNoteUseCase
import com.ahrokholska.note_details.domain.useCase.PinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.UnPinNoteUseCase
import com.ahrokholska.note_details.domain.useCase.getNoteDetails.GetRoutineTasksNoteDetailsUseCase
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

@HiltViewModel(assistedFactory = RoutineTasksDetailsScreenViewModel.Factory::class)
internal class RoutineTasksDetailsScreenViewModel @AssistedInject constructor(
    @Assisted id: Int,
    pinNoteUseCase: PinNoteUseCase,
    unPinNoteUseCase: UnPinNoteUseCase,
    getRoutineTasksNoteDetailsUseCase: GetRoutineTasksNoteDetailsUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    finishNoteUseCase: FinishNoteUseCase,
    private val changeRoutineTasksSubNoteCheckUseCase: ChangeRoutineTasksSubNoteCheckUseCase
) : NoteDetailsViewModel(
    id, NoteType.RoutineTasks, pinNoteUseCase, unPinNoteUseCase, deleteNoteUseCase,
    finishNoteUseCase
) {
    val note = getRoutineTasksNoteDetailsUseCase(id)
        .map { it?.toPresentation() }
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

    @AssistedFactory
    interface Factory {
        fun create(id: Int): RoutineTasksDetailsScreenViewModel
    }
}