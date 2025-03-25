package com.ahrokholska.notes.domain.useCase.getAllNotes

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

class GetAllGuidanceNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getAllGuidanceNotes()
}