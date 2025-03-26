package com.ahrokholska.note_details.domain.useCase.getNoteDetails

import com.ahrokholska.api.NotesRepository
import com.ahrokholska.api.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetRoutineTasksNoteDetailsUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke(id: Int): Flow<Note.RoutineTasks?> =
        notesRepository.getRoutineTasksNoteDetails(id)
}