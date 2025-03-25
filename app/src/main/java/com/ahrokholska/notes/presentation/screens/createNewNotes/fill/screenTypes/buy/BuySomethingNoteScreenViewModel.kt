package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.buy

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.Note
import com.ahrokholska.notes.domain.useCase.SaveNoteUseCase
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuySomethingNoteScreenViewModel @Inject constructor(private val saveNoteUseCase: SaveNoteUseCase) :
    NoteWithTitleViewModel() {
    private val _items = MutableStateFlow(listOf("" to false))
    val items = _items.asStateFlow()

    fun addItem() {
        viewModelScope.launch {
            _items.update { it + ("" to false) }
        }
    }

    fun changeItem(item: String, index: Int) {
        viewModelScope.launch {
            _items.update { it.toMutableList().apply { this[index] = item to false } }
        }
    }

    fun saveNote(title: String, items: List<Pair<String, Boolean>>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            var hasErrors = false
            items.forEachIndexed { index, item ->
                if (item.first.isBlank()) {
                    hasErrors = true
                    _items.update { it.toMutableList().apply { this[index] = item.first to true } }
                }
            }
            if (!hasErrors) {
                saveNote {
                    saveNoteUseCase(
                        Note.BuyingSomething(
                            title = title,
                            items = items.map { false to it.first })
                    )
                    onSuccess()
                }
            }
        }
    }
}