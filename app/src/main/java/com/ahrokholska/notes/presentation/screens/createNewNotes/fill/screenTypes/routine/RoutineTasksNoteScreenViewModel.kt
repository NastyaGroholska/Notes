package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.routine

import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.useCase.SaveNoteUseCase
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.NoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineTasksNoteScreenViewModel @Inject constructor(private val saveNoteUseCase: SaveNoteUseCase) :
    NoteViewModel() {
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

    fun saveNote(items: List<Pair<String, String>>, onSuccess: () -> Unit) = saveNote {
        //TODO validation
        saveNoteUseCase(
            Note.RoutineTasks(
                active = items.map {
                    Note.RoutineTasks.SubNote(
                        title = it.first,
                        text = it.second
                    )
                },
                completed = listOf()
            )
        )
        onSuccess()
    }
}