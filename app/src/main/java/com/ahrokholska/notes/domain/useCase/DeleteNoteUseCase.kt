package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.api.NotesRepository
import com.ahrokholska.api.model.NoteType
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, noteType: NoteType) =
        notesRepository.deleteNote(noteId, noteType)
}