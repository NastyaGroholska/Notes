package com.ahrokholska.notes_home.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

internal class GetPinnedNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getLast10PinnedNotes()
}