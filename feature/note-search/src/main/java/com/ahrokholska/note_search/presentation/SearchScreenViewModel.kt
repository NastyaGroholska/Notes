package com.ahrokholska.note_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.NoteTitle
import com.ahrokholska.note_search.domain.useCase.GetAllTitlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchScreenViewModel @Inject constructor(
    getAllTitlesUseCase: GetAllTitlesUseCase
) : ViewModel() {
    private val filter = MutableStateFlow("")
    val titles = getAllTitlesUseCase()
        .combine(filter) { titles, filter ->
            if (filter.isEmpty()) {
                titles.map { it to (0 to 0) }
            } else {
                val newList = mutableListOf<Pair<NoteTitle, Pair<Int, Int>>>()
                titles.forEach {
                    val index = it.title.indexOf(filter)
                    if (index != -1) {
                        newList.add(it to (index to (index + filter.length)))
                    }
                }
                newList
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    fun changeSearch(search: String) {
        viewModelScope.launch {
            filter.update { search }
        }
    }
}