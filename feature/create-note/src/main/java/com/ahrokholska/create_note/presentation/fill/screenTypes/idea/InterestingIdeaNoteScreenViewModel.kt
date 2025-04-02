package com.ahrokholska.create_note.presentation.fill.screenTypes.idea

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.Note
import com.ahrokholska.create_note.domain.useCase.SaveNoteUseCase
import com.ahrokholska.create_note.presentation.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InterestingIdeaNoteScreenViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase
) : NoteWithTitleViewModel() {
    private val _body = MutableStateFlow("")
    val body = _body.asStateFlow()

    fun changeBody(body: String) {
        if (isSaving) return
        viewModelScope.launch {
            _body.update { body }
        }
    }

    fun saveNote(title: String, body: String, onSuccess: () -> Unit) =
        saveNote {
            saveNoteUseCase(Note.InterestingIdea(title = title, body = body))
            onSuccess()
        }
}