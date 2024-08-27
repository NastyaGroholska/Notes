package com.ahrokholska.notes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.mapper.toUI
import com.ahrokholska.notes.domain.useCase.GetAllNoteListsUseCase
import com.ahrokholska.notes.domain.useCase.GetPinnedNotesUseCase
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.theme.noteColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getPinnedNotesUseCase: GetPinnedNotesUseCase,
    getAllNoteListsUseCase: GetAllNoteListsUseCase
) : ViewModel() {
    val pinnedNotes = getPinnedNotesUseCase().map { list ->
        list.mapIndexed { index, item ->
            Note(
                title = item.title,
                text = item.text,
                type = item.type.toUI(),
                color = noteColors[index % noteColors.size]
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val allNotes = getAllNoteListsUseCase().map { lists ->
        lists.mapIndexed { index1, list ->
            list.mapIndexed { index2, item ->
                Note(
                    title = item.title,
                    text = item.text,
                    type = item.type.toUI(),
                    color = noteColors[(index1 + index2 + 1) % noteColors.size]
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}