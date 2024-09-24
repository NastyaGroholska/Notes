package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class ChangeBuySomethingItemCheckUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, index: Int, checked: Boolean) =
        notesRepository.changeBuySomethingItemCheck(noteId, index, checked)
}