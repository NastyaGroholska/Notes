package com.ahrokholska.notes_home.domain.useCase.getLast10Notes

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

internal class GetLast10RoutineTasksNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10RoutineTasksNotes()
}