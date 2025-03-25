package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

class GetPinnedNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10PinnedNotes()
}