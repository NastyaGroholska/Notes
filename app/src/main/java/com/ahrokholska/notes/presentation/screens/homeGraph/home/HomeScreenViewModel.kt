package com.ahrokholska.notes.presentation.screens.homeGraph.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.NotePreview
import com.ahrokholska.notes.domain.useCase.GetPinnedNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10BuySomethingNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10GoalsNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10GuidanceNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10InterestingIdeaNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10RoutineTasksNotesUseCase
import com.ahrokholska.notes.presentation.mapper.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getLast10InterestingIdeaNotesUseCase: GetLast10InterestingIdeaNotesUseCase,
    getLast10BuySomethingNotesUseCase: GetLast10BuySomethingNotesUseCase,
    getLast10GoalsNotesUseCase: GetLast10GoalsNotesUseCase,
    getLast10GuidanceNotesUseCase: GetLast10GuidanceNotesUseCase,
    getLast10RoutineTasksNotesUseCase: GetLast10RoutineTasksNotesUseCase,
    getPinnedNotesUseCase: GetPinnedNotesUseCase,
) : ViewModel() {
    val pinnedNotes = getPinnedNotesUseCase().map { list ->
        list.mapIndexed { index, item ->
            when (item) {
                is NotePreview.BuyingSomething -> item.toUI(
                    index = index, inCombinedList = true
                )

                is NotePreview.Goals -> item.toUI(
                    index = index, inCombinedList = true
                )

                is NotePreview.Guidance -> item.toUI(
                    index = index, inCombinedList = true
                )

                is NotePreview.InterestingIdea -> item.toUI(
                    index = index, inCombinedList = true
                )

                is NotePreview.RoutineTasks -> item.toUI(
                    index = index, inCombinedList = true
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val interestingIdeaNotes = getLast10InterestingIdeaNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item -> item.toUI(index) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val buySomethingNotes = getLast10BuySomethingNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item -> item.toUI(index) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val goalsNotes = getLast10GoalsNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item -> item.toUI(index) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val guidanceNotes = getLast10GuidanceNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item -> item.toUI(index) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val routineTasksNotes = getLast10RoutineTasksNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item -> item.toUI(index) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)
}