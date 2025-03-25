package com.ahrokholska.notes.domain.useCase.getAllNotes

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

class GetAllPinnedNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getAllPinnedNotes()
}