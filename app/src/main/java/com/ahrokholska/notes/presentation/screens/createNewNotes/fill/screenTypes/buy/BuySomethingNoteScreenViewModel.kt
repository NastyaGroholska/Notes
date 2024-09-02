package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.buy

import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuySomethingNoteScreenViewModel @Inject constructor() : NoteWithTitleViewModel() {
    private val _items = MutableStateFlow(listOf(""))
    val items = _items.asStateFlow()

    fun addItem() {
        viewModelScope.launch {
            _items.update { it + "" }
        }
    }

    fun changeItem(item: String, index: Int) {
        viewModelScope.launch {
            _items.update { it.toMutableList().apply { this[index] = item } }
        }
    }
}