package com.ahrokholska.notes_home.presentation.allNotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.notes_home.domain.useCase.getAllNotes.GetAllBuySomethingNotesUseCase
import com.ahrokholska.notes_home.domain.useCase.getAllNotes.GetAllGoalsNotesUseCase
import com.ahrokholska.notes_home.domain.useCase.getAllNotes.GetAllGuidanceNotesUseCase
import com.ahrokholska.notes_home.domain.useCase.getAllNotes.GetAllInterestingIdeaNotesUseCase
import com.ahrokholska.notes_home.domain.useCase.getAllNotes.GetAllRoutineTasksNotesUseCase
import com.ahrokholska.presentation_domain_mapper.toUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = AllNotesScreenViewModel.Factory::class)
internal class AllNotesScreenViewModel @AssistedInject constructor(
    @Assisted type: NoteType,
    getAllBuySomethingNotesUseCase: GetAllBuySomethingNotesUseCase,
    getAllGoalsNotesUseCase: GetAllGoalsNotesUseCase,
    getAllGuidanceNotesUseCase: GetAllGuidanceNotesUseCase,
    getAllInterestingIdeaNotesUseCase: GetAllInterestingIdeaNotesUseCase,
    getAllRoutineTasksNotesUseCase: GetAllRoutineTasksNotesUseCase,
) : ViewModel() {
    val notes = when (type) {
        NoteType.InterestingIdea -> getAllInterestingIdeaNotesUseCase()
        NoteType.BuyingSomething -> getAllBuySomethingNotesUseCase()
        NoteType.Goals -> getAllGoalsNotesUseCase()
        NoteType.Guidance -> getAllGuidanceNotesUseCase()
        NoteType.RoutineTasks -> getAllRoutineTasksNotesUseCase()
    }.map { list ->
        list.map { it.toUI(0) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    @AssistedFactory
    interface Factory {
        fun create(type: NoteType): AllNotesScreenViewModel
    }
}