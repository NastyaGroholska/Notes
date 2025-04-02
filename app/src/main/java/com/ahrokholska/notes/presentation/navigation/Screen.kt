package com.ahrokholska.notes.presentation.navigation

import com.ahrokholska.note_presentation.model.NoteType
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
    data object Settings : Screen()
}