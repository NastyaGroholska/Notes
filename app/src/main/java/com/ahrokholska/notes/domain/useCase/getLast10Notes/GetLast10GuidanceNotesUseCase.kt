package com.ahrokholska.notes.domain.useCase.getLast10Notes

import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class GetLast10GuidanceNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10GuidanceNotes()
}