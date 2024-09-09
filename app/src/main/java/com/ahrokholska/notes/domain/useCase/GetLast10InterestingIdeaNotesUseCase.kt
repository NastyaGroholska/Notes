package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class GetLast10InterestingIdeaNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10InterestingIdeaNotes()
}