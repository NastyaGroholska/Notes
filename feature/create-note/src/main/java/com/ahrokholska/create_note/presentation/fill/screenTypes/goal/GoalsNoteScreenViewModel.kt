package com.ahrokholska.create_note.presentation.fill.screenTypes.goal

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.Note
import com.ahrokholska.create_note.domain.useCase.SaveNoteUseCase
import com.ahrokholska.create_note.presentation.fill.screenTypes.NoteWithTitleViewModel
import com.ahrokholska.create_note.presentation.fill.screenTypes.TextAndError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GoalsNoteScreenViewModel @Inject constructor(private val saveNoteUseCase: SaveNoteUseCase) :
    NoteWithTitleViewModel() {
    private val _items = MutableStateFlow(listOf(TextAndError("") to listOf<TextAndError>()))
    val items = _items.asStateFlow()

    fun addMainItem() {
        if (isSaving) return
        viewModelScope.launch {
            _items.update { it.toMutableList().apply { add(TextAndError("") to listOf()) } }
        }
    }

    fun addSubItem(index: Int) {
        if (isSaving) return
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index] = this[index].copy(
                        second = this[index].second.toMutableList().apply { add(TextAndError("")) }
                    )
                }
            }
        }
    }

    fun mainItemChange(item: String, index: Int) {
        if (isSaving) return
        viewModelScope.launch {
            _items.update {
                it.toMutableList()
                    .apply { this[index] = this[index].copy(first = TextAndError(item)) }
            }
        }
    }

    fun subItemChange(item: String, index1: Int, index2: Int) {
        if (isSaving) return
        viewModelScope.launch {
            _items.update {
                it.toMutableList().apply {
                    this[index1] = this[index1].copy(
                        second = this[index1].second.toMutableList()
                            .apply { this[index2] = TextAndError(item) }
                    )
                }
            }
        }
    }

    fun saveNote(
        title: String,
        tasks: List<Pair<TextAndError, List<TextAndError>>>,
        onSuccess: () -> Unit
    ) = saveNote {
        var hasErrors = false
        tasks.forEachIndexed { index1, item ->      //TODO
            if (item.first.text.isBlank()) {
                hasErrors = true
                _items.update {
                    it.toMutableList().apply {
                        this[index1] = this[index1].copy(first = item.first.copy(error = true))
                    }
                }
            }
            item.second.forEachIndexed { index2, subitem ->
                if (subitem.text.isBlank()) {
                    hasErrors = true
                    _items.update {
                        it.toMutableList().apply {
                            this[index1] = this[index1].copy(
                                second = this[index1].second.toMutableList()
                                    .apply { this[index2] = this[index2].copy(error = true) }
                            )
                        }
                    }
                }
            }
        }
        if (!hasErrors) {
            saveNoteUseCase(
                Note.Goals(
                    title = title,
                    tasks = tasks.map { pair ->
                        Note.Goals.Task(
                            finished = false,
                            text = pair.first.text
                        ) to pair.second.map {
                            Note.Goals.Task(
                                finished = false,
                                text = it.text
                            )
                        }
                    }
                )
            )
            onSuccess()
        }
    }
}