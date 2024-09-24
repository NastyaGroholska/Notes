package com.ahrokholska.notes.presentation.screens.noteDetails.buy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.ChangeBuySomethingItemCheckUseCase
import com.ahrokholska.notes.domain.useCase.getNoteDetails.GetBuySomethingNoteDetailsUseCase
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.theme.noteColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuySomethingDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBuySomethingNoteDetailsUseCase: GetBuySomethingNoteDetailsUseCase,
    private val changeBuySomethingItemCheckUseCase: ChangeBuySomethingItemCheckUseCase
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    val note = getBuySomethingNoteDetailsUseCase(id)
        .map {
            Note.BuyingSomething(
                id = it.id,
                title = it.title,
                items = it.items,
                isFinished = it.isFinished,
                isPinned = it.isPinned,
                color = noteColors[0]
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun changeItemCheck(index: Int, checked: Boolean) {
        viewModelScope.launch {
            changeBuySomethingItemCheckUseCase(id, index, checked)
        }
    }
}