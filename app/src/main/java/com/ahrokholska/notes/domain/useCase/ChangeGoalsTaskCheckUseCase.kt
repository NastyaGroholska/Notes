package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

class ChangeGoalsTaskCheckUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, index: Int, checked: Boolean) =
        notesRepository.changeGoalsTaskCheck(noteId, index, checked)
}