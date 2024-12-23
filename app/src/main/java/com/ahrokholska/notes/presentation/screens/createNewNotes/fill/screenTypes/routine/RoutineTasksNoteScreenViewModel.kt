package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.routine

import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.useCase.SaveNoteUseCase
import com.ahrokholska.notes.presentation.model.TextAndError
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
    private val _items = MutableStateFlow(listOf(TextAndError("") to TextAndError("")))
    val items = _items.asStateFlow()

    fun addItem() {
        viewModelScope.launch {
            _items.update { it + (TextAndError("") to TextAndError("")) }
        }
    }

    fun changeItemTitle(title: String, index: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(first = TextAndError(title))
                }
            }
        }
    }

    fun changeItemBody(body: String, index: Int) {
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(second = TextAndError(body))
                }
            }
        }
    }

    fun saveNote(items: List<Pair<TextAndError, TextAndError>>, onSuccess: () -> Unit) =
        viewModelScope.launch {
            var hasErrors = false
            items.forEachIndexed { index, pair ->
                if (pair.first.text.isBlank()) {
                    hasErrors = true
                    _items.update {
                        it.toMutableList().apply {
                            this[index] =
                                this[index].copy(first = this[index].first.copy(error = true))
                        }
                    }
                }
                if (pair.second.text.isBlank()) {
                    hasErrors = true
                    _items.update {
                        it.toMutableList().apply {
                            this[index] =
                                this[index].copy(second = this[index].second.copy(error = true))
                        }
                    }
                }
            }
            if (!hasErrors) {
                saveNote {
                    saveNoteUseCase(
                        Note.RoutineTasks(
                            active = items.map {
                                Note.RoutineTasks.SubNote(
                                    title = it.first.text,
                                    text = it.second.text
                                )
                            },
                            completed = listOf()
                        )
                    )
                    onSuccess()
                }
            }
        }
}