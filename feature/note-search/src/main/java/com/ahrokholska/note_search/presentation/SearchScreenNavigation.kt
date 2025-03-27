package com.ahrokholska.note_search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.presentation_domain_mapper.toUI
import kotlinx.serialization.Serializable

@Serializable
internal data object SearchNotes

fun NavGraphBuilder.noteSearchScreen(
    onExit: () -> Unit,
    onNoteClick: (Int, NoteType) -> Unit,
    decoration: @Composable (content: @Composable (scrollBottomPadding: Dp) -> Unit) -> Unit =
        @Composable { content -> content(0.dp) }
) {
    composable<SearchNotes> {
        SearchScreen(
            decoration = decoration,
            onBackClick = onExit,
            onNoteClick = { id, type ->
                onNoteClick(id, type.toUI())
            },
        )
    }
}

fun NavController.navigateToNoteSearchScreen(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = SearchNotes, builder = builder)
}

fun NavOptionsBuilder.popUpToNoteSearchScreen(inclusive: Boolean = false) =
    popUpTo(SearchNotes) {
        this.inclusive = inclusive
    }
