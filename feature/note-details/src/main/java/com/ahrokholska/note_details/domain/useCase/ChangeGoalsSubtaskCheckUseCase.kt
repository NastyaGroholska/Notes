package com.ahrokholska.note_details.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

internal class ChangeGoalsSubtaskCheckUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, taskIndex: Int, subtaskIndex: Int, checked: Boolean) =
        notesRepository.changeGoalsSubtaskCheck(noteId, taskIndex, subtaskIndex, checked)
}