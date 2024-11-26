package com.ahrokholska.notes.presentation.navigation

import com.ahrokholska.notes.presentation.model.NoteType
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeGraph : Screen() {
        @Serializable
        data object Home : Screen()

        @Serializable
        data class AllNotes(val type: NoteType) : Screen()

        @Serializable
        data object AllPinnedNotes : Screen()
    }

    @Serializable
    data object CreateNewNotesGraph : Screen() {
        @Serializable
        data object SelectNoteType : Screen()

        @Serializable
        data class CreateNote(val type: NoteType) : Screen()
    }

    @Serializable
    data object AllFinishedNotes : Screen()

    @Serializable
    data object SearchNotes : Screen()

    @Serializable
    data class NoteDetails(val id: Int, val type: NoteType) : Screen()
}