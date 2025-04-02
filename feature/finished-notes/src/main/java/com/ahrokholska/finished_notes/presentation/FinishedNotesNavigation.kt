package com.ahrokholska.finished_notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.ahrokholska.note_presentation.model.NoteType
import kotlinx.serialization.Serializable

@Serializable
internal data object AllFinishedNotes

fun NavGraphBuilder.noteAllFinishedNotesScreen(
    onNoteClick: (Int, NoteType) -> Unit,
    decoration: @Composable (content: @Composable (scrollBottomPadding: Dp) -> Unit) -> Unit =
        @Composable { content -> content(0.dp) }
) {
    composable<AllFinishedNotes> {
        FinishedNotesScreen(
            decoration = decoration,
            onNoteClick = onNoteClick,
        )
    }
}

fun NavController.navigateToAllFinishedNotesScreen(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AllFinishedNotes, builder = builder)
}

fun NavOptionsBuilder.popUpToAllFinishedNotesScreen(inclusive: Boolean = false) =
    popUpTo(AllFinishedNotes) {
        this.inclusive = inclusive
    }