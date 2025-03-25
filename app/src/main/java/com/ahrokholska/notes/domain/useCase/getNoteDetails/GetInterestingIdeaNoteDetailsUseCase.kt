package com.ahrokholska.notes.domain.useCase.getNoteDetails

import com.ahrokholska.api.NotesRepository
import com.ahrokholska.api.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterestingIdeaNoteDetailsUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    operator fun invoke(id: Int): Flow<Note.InterestingIdea?> =
        notesRepository.getInterestingIdeaNoteDetails(id)
}