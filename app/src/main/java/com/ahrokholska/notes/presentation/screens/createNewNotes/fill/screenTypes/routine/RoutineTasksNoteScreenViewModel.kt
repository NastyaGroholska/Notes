package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineTasksNoteScreenViewModel @Inject constructor() : ViewModel() {
    private val _items = MutableStateFlow(listOf("" to ""))
    val items = _items.asStateFlow()

    fun addItem() {
        viewModelScope.launch {
            _items.update { it + ("" to "") }
        }
    }

    fun changeItemTitle(title: String, index: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(first = title)
                }
            }
        }
    }

    fun changeItemBody(body: String, index: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(second = body)
                }
            }
        }
    }
}