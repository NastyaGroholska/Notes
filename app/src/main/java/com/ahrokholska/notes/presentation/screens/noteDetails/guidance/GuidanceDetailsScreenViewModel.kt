package com.ahrokholska.notes.presentation.screens.noteDetails.guidance

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetGuidanceNoteDetailsUseCase
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.theme.noteColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GuidanceDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getGuidanceNoteDetailsUseCase: GetGuidanceNoteDetailsUseCase
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    val note = getGuidanceNoteDetailsUseCase(id)
        .map {
            Note.Guidance(
                id = it.id,
                title = it.title,
                body = it.body,
                image = it.image,
                isFinished = it.isFinished,
                isPinned = it.isPinned,
                color = noteColors[0]
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}