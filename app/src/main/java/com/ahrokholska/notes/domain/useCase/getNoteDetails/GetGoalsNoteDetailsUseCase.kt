package com.ahrokholska.notes.domain.useCase.getNoteDetails

import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalsNoteDetailsUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke(id: Int): Flow<Note.Goals?> = notesRepository.getGoalsNoteDetails(id)
}