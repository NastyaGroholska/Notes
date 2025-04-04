package com.ahrokholska.notes_home.domain.useCase.getLast10Notes

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

internal class GetLast10GoalsNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10GoalsNotes()
}