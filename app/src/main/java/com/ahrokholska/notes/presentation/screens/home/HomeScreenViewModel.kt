package com.ahrokholska.notes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.GetPinnedNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10BuySomethingNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10GoalsNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10GuidanceNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10InterestingIdeaNotesUseCase
import com.ahrokholska.notes.presentation.model.NotePreview
import com.ahrokholska.notes.presentation.theme.noteColors
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
    getPinnedNotesUseCase: GetPinnedNotesUseCase,
) : ViewModel() {
    val pinnedNotes = getPinnedNotesUseCase().map { list ->
        list.mapIndexed { index, item ->
            NotePreview.Guidance(
                title = item.title,
                body = item.body,
                image = item.image,
                color = noteColors[index % noteColors.size]
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val interestingIdeaNotes = getLast10InterestingIdeaNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item ->
                NotePreview.InterestingIdea(
                    id = item.id,
                    title = item.title,
                    body = item.body,
                    color = noteColors[(index + 1) % noteColors.size]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val buySomethingNotes = getLast10BuySomethingNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item ->
                NotePreview.BuyingSomething(
                    id = item.id,
                    title = item.title,
                    items = item.items,
                    color = noteColors[(index + 2) % noteColors.size]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val goalsNotes = getLast10GoalsNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item ->
                NotePreview.Goals(
                    id = item.id,
                    title = item.title,
                    tasks = item.tasks,
                    color = noteColors[(index + 3) % noteColors.size]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val guidanceNotes = getLast10GuidanceNotesUseCase()
        .map { list ->
            list.mapIndexed { index, item ->
                NotePreview.Guidance(
                    id = item.id,
                    title = item.title,
                    body = item.body,
                    image = item.image,
                    color = noteColors[(index + 4) % noteColors.size]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}