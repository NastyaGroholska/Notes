package com.ahrokholska.note_details.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

internal class ChangeBuySomethingItemCheckUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteId: Int, index: Int, checked: Boolean) =
        notesRepository.changeBuySomethingItemCheck(noteId, index, checked)
}