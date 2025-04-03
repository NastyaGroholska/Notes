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

    fun saveNote(onSuccess: () -> Unit) = super.saveNote {
        var hasErrors = false
        val validatedList = _items.value.toMutableList()
        validatedList.forEachIndexed { index, pair ->
            if (pair.first.text.isBlank()) {
                hasErrors = true
                validatedList[index] =
                    validatedList[index].copy(first = validatedList[index].first.copy(error = true))
            }
            if (pair.second.text.isBlank()) {
                hasErrors = true
                validatedList[index] =
                    validatedList[index].copy(second = validatedList[index].second.copy(error = true))
            }
        }
        _items.update { validatedList }
        if (!hasErrors) {
            saveNoteUseCase(
                Note.RoutineTasks(
                    active = validatedList.map {
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