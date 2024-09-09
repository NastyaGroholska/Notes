package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.idea

import androidx.lifecycle.viewModelScope
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.useCase.SaveNoteUseCase
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestingIdeaNoteScreenViewModel @Inject constructor(private val saveNoteUseCase: SaveNoteUseCase) :
    NoteWithTitleViewModel() {
    private val _body = MutableStateFlow("")
    val body = _body.asStateFlow()

    private var isSaving = false

    fun changeBody(body: String) {
        viewModelScope.launch {
            _body.update { body }
        }
    }

    fun saveNote(title: String, body: String, onSuccess: () -> Unit) {
        if (isSaving) return
        isSaving = true
        viewModelScope.launch {
            saveNoteUseCase(Note.InterestingIdea(title = title, body = body))
            onSuccess()
        }
    }
}