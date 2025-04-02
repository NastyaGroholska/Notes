package com.ahrokholska.create_note.presentation.fill.screenTypes.routine

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.Note
import com.ahrokholska.create_note.domain.useCase.SaveNoteUseCase
import com.ahrokholska.create_note.presentation.fill.screenTypes.NoteViewModel
import com.ahrokholska.create_note.presentation.fill.screenTypes.TextAndError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RoutineTasksNoteScreenViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase
) : NoteViewModel() {
    private val _items = MutableStateFlow(listOf(TextAndError("") to TextAndError("")))
    val items = _items.asStateFlow()

    fun addItem() {
        if (isSaving) return
        viewModelScope.launch {
            _items.update { it + (TextAndError("") to TextAndError("")) }
        }
    }

    fun changeItemTitle(title: String, index: Int) {
        if (isSaving) return
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(first = TextAndError(title))
                }
            }
        }
    }

    fun changeItemBody(body: String, index: Int) {
        if (isSaving) return
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(second = TextAndError(body))
                }
            }
        }
    }

    fun saveNote(items: List<Pair<TextAndError, TextAndError>>, onSuccess: () -> Unit) = saveNote {
        var hasErrors = false
        items.forEachIndexed { index, pair ->   //TODO
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