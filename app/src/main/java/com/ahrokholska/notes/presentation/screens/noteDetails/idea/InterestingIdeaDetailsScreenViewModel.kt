package com.ahrokholska.notes.presentation.screens.noteDetails.idea

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetInterestingIdeaNoteDetailsUseCase
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.theme.noteColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InterestingIdeaDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getInterestingIdeaNoteDetailsUseCase: GetInterestingIdeaNoteDetailsUseCase
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    val note = getInterestingIdeaNoteDetailsUseCase(id)
        .map {
            Note.InterestingIdea(
                id = it.id,
                title = it.title,
                body = it.body,
                isFinished = it.isFinished,
                isPinned = it.isPinned,
                color = noteColors[0]
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}