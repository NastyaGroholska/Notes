package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class ChangeRoutineTasksSubNoteCheckUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, index: Int, finished: Boolean) =
        notesRepository.changeRoutineTasksSubNoteCheck(noteId, index, finished)
}