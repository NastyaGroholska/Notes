package com.ahrokholska.create_note.presentation.fill.screenTypes.buy

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
internal class BuySomethingNoteScreenViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase
) : NoteWithTitleViewModel() {
    private val _items = MutableStateFlow(listOf(TextAndError("")))
    val items = _items.asStateFlow()

    fun addItem() {
        if (isSaving) return
        viewModelScope.launch {
            _items.update { it + TextAndError("") }
        }
    }

    fun changeItem(item: String, index: Int) {
        if (isSaving) return
        viewModelScope.launch {
            _items.update { it.toMutableList().apply { this[index] = TextAndError(item) } }
        }
    }

    fun saveNote(title: String, onSuccess: () -> Unit) =
        saveNote {
            var hasErrors = false
            val validatedList = _items.value.toMutableList()
            validatedList.forEachIndexed { index, item ->
                if (item.text.isBlank()) {
                    hasErrors = true
                    validatedList[index] = validatedList[index].copy(error = true)
                }
            }
            _items.update { validatedList }
            if (!hasErrors) {
                saveNoteUseCase(
                    Note.BuyingSomething(
                        title = title,
                        items = validatedList.map { false to it.text })
                )
                onSuccess()
            }
        }
}