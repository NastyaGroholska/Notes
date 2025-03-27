package com.ahrokholska.note_search.domain.useCase

import com.ahrokholska.api.NotesRepository
import javax.inject.Inject

internal class GetAllTitlesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getAllTitles()
}