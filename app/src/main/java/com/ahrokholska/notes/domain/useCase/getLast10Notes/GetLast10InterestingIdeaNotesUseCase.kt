package com.ahrokholska.notes.domain.useCase.getLast10Notes

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

class GetLast10InterestingIdeaNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10InterestingIdeaNotes()
}