package com.ahrokholska.notes.domain.useCase.getAllNotes

import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class GetAllBuySomethingNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getAllBuySomethingNotes()
}