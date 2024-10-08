package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.goal

import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.useCase.SaveNoteUseCase
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GoalsNoteScreenViewModel @javax.inject.Inject constructor(private val saveNoteUseCase: SaveNoteUseCase) :
    NoteWithTitleViewModel() {
    private val _items = MutableStateFlow(listOf("" to listOf<String>()))
    val items = _items.asStateFlow()

    fun addMainItem() {
        viewModelScope.launch {
            _items.update { it.toMutableList().apply { add("" to listOf()) } }
        }
    }

    fun addSubItem(index: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(
                        second = this[index].second.toMutableList().apply { add("") }
                    )
                }
            }
        }
    }

    fun mainItemChange(item: String, index: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply { this[index] = this[index].copy(first = item) }
            }
        }
    }

    fun subItemChange(item: String, index1: Int, index2: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index1] = this[index1].copy(
                        second = this[index1].second.toMutableList().apply { this[index2] = item }
                    )
                }
            }
        }
    }

    fun saveNote(title: String, tasks: List<Pair<String, List<String>>>, onSuccess: () -> Unit) =
        saveNote {
            saveNoteUseCase(
                Note.Goals(
                    title = title,
                    tasks = tasks.map { pair ->
                        Note.Goals.Task(finished = false, text = pair.first) to
                                pair.second.map { Note.Goals.Task(finished = false, text = it) }
                    }
                )
            )
            onSuccess()
        }
}