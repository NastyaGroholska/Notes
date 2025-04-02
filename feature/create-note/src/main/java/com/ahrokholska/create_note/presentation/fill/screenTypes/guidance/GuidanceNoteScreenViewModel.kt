package com.ahrokholska.create_note.presentation.fill.screenTypes.guidance

import androidx.lifecycle.viewModelScope
import com.ahrokholska.api.model.Note
import com.ahrokholska.create_note.domain.useCase.CopyAndUpdateImageUseCase
import com.ahrokholska.create_note.domain.useCase.SaveNoteUseCase
import com.ahrokholska.create_note.presentation.fill.screenTypes.NoteWithTitleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GuidanceNoteScreenViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val copyAndUpdateImageUseCase: CopyAndUpdateImageUseCase
) : NoteWithTitleViewModel() {
    private val _body = MutableStateFlow("")
    val body = _body.asStateFlow()
    private val _image = MutableStateFlow("")
    val image = _image.asStateFlow()

    fun changeBody(body: String) {
        if (isSaving) return
        viewModelScope.launch {
            _body.update { body }
        }
    }

    fun changeImage(uri: String) {
        if (isSaving) return
        viewModelScope.launch {
            _image.update { uri }
        }
    }

    fun saveNote(title: String, body: String, image: String, onSuccess: () -> Unit) =
        saveNote {
            saveNoteUseCase(Note.Guidance(title = title, body = body, image = image)).onSuccess {
                copyAndUpdateImageUseCase(image)
                onSuccess()
            }
        }
}