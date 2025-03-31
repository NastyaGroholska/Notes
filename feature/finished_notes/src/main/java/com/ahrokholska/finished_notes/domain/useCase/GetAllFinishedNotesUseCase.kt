package com.ahrokholska.finished_notes.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

class GetAllFinishedNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getAllFinishedNotes()
}