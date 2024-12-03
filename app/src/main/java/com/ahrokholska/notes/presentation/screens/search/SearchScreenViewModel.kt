package com.ahrokholska.notes.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.useCase.GetAllTitlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    getAllTitlesUseCase: GetAllTitlesUseCase
) : ViewModel() {
    private val filter = MutableStateFlow("")
    val titles = getAllTitlesUseCase()
        .combine(filter) { titles, filter ->
            if (filter.isEmpty()) titles else titles.filter { it.title.contains(filter) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    fun changeSearch(search: String) {
        viewModelScope.launch {
            filter.update { search }
        }
    }
}