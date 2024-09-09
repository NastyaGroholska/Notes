package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(note: Note) = notesRepository.saveNote(note)
}