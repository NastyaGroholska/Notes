package com.ahrokholska.notes.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.GetAllTitlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    getAllTitlesUseCase: GetAllTitlesUseCase
) : ViewModel() {
    val titles = getAllTitlesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, listOf())
}