package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.api.NotesRepository
import com.ahrokholska.api.model.Note
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(note: Note) = notesRepository.saveNote(note)
}