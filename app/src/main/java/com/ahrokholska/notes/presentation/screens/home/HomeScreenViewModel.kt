package com.ahrokholska.notes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.mapper.toUI
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.useCase.GetAllNoteListsUseCase
import com.ahrokholska.notes.domain.useCase.GetPinnedNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10BuySomethingNotesUseCase
import com.ahrokholska.notes.domain.useCase.getLast10Notes.GetLast10InterestingIdeaNotesUseCase
import com.ahrokholska.notes.presentation.model.Note2
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
    getPinnedNotesUseCase: GetPinnedNotesUseCase,
    getAllNoteListsUseCase: GetAllNoteListsUseCase
) : ViewModel() {
    val pinnedNotes = getPinnedNotesUseCase().map { list ->
        list.mapIndexed { index, item ->
            Note2(
                title = "item.title",
                text = "item.body",
                type = NoteType.Goals.toUI(),
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
                    color = noteColors[index % noteColors.size]
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
                    color = noteColors[index % noteColors.size]
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val allNotes = getAllNoteListsUseCase().map { lists ->
        lists.mapIndexed { index1, list ->
            list.mapIndexed { index2, item ->
                Note2(
                    title = "item.title",
                    text = "item.body",
                    type = NoteType.Guidance.toUI(),
                    color = noteColors[(index1 + index2 + 1) % noteColors.size]
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}