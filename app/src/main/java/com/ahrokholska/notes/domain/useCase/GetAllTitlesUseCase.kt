package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.repository.NotesRepository
import javax.inject.Inject

class GetAllTitlesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke() = notesRepository.getAllTitles()
}