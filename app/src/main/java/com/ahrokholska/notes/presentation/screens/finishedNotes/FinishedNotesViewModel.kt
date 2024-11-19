package com.ahrokholska.notes.presentation.screens.finishedNotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllFinishedNotesUseCase
import com.ahrokholska.notes.presentation.mapper.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FinishedNotesViewModel @Inject constructor(getAllFinishedNotesUseCase: GetAllFinishedNotesUseCase) :
    ViewModel() {
    val notes = getAllFinishedNotesUseCase().map { list ->
        list.mapIndexed { index, item -> item.toUI(index) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}