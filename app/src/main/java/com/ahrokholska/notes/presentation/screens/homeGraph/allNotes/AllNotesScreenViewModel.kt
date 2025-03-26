package com.ahrokholska.notes.presentation.screens.homeGraph.allNotes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllBuySomethingNotesUseCase
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllGoalsNotesUseCase
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllGuidanceNotesUseCase
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllInterestingIdeaNotesUseCase
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllRoutineTasksNotesUseCase
import com.ahrokholska.notes.presentation.mapper.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AllNotesScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAllBuySomethingNotesUseCase: GetAllBuySomethingNotesUseCase,
    getAllGoalsNotesUseCase: GetAllGoalsNotesUseCase,
    getAllGuidanceNotesUseCase: GetAllGuidanceNotesUseCase,
    getAllInterestingIdeaNotesUseCase: GetAllInterestingIdeaNotesUseCase,
    getAllRoutineTasksNotesUseCase: GetAllRoutineTasksNotesUseCase,
) : ViewModel() {
    val type = savedStateHandle.get<NoteType>("type")

    val notes = when (type) {
        NoteType.InterestingIdea -> getAllInterestingIdeaNotesUseCase()
        NoteType.BuyingSomething -> getAllBuySomethingNotesUseCase()
        NoteType.Goals -> getAllGoalsNotesUseCase()
        NoteType.Guidance -> getAllGuidanceNotesUseCase()
        NoteType.RoutineTasks -> getAllRoutineTasksNotesUseCase()
        null -> flowOf(listOf())
    }.map { list ->
        list.map { it.toUI(0) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}