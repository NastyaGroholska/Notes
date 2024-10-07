package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class UnPinNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, noteType: NoteType) =
        notesRepository.unpinNote(noteId, noteType)
}