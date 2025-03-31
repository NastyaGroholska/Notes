package com.ahrokholska.notes.presentation.screens.homeGraph.allPinnedNotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.getAllNotes.GetAllPinnedNotesUseCase
import com.ahrokholska.presentation_domain_mapper.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AllPinnedNotesScreenViewModel @Inject constructor(getAllPinnedNotesUseCase: GetAllPinnedNotesUseCase) :
    ViewModel() {
    val notes = getAllPinnedNotesUseCase().map { list ->
        list.mapIndexed { index, item -> item.toUI(index) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}