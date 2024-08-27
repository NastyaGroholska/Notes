package com.ahrokholska.notes.domain.useCase

import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAllNoteListsUseCase @Inject constructor() {
    operator fun invoke() = flowOf(
        listOf(
            listOf(
                Note(
                    title = "\uD83D\uDCA1 New Product Idea Design",
                    text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n\n" +
                            "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
                    type = NoteType.InterestingIdea,
                ),
                Note(
                    title = "\uD83D\uDCA1 New Product Idea Design",
                    text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement.",
                    type = NoteType.InterestingIdea,
                )
            ),
            listOf(
                Note(
                    title = "\uD83D\uDCA1 New Product Idea Design",
                    text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n\n" +
                            "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
                    type = NoteType.Guidance,
                ),
                Note(
                    title = "\uD83D\uDCA1 New Product Idea Design",
                    text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement.",
                    type = NoteType.Guidance,
                )
            )
        )
    )
}