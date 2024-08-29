package com.ahrokholska.notes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object CreateNewNotesGraph : Screen(){
        @Serializable
        data object SelectNoteType : Screen()
    }
}