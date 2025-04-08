package com.ahrokholska.note_details.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ahrokholska.note_details.presentation.NoteDetailsScreen
import com.ahrokholska.note_presentation.model.NoteType
import kotlinx.serialization.Serializable

@Serializable
internal data class NoteDetails(val id: Int, val type: NoteType)

fun NavGraphBuilder.noteDetailsScreen(onExit: () -> Unit) {
    composable<NoteDetails> {
        val args = it.toRoute<NoteDetails>()
        NoteDetailsScreen(
            id = args.id,
            type = args.type,
            onBackClick = onExit
        )
    }
}

fun NavController.navigateToNoteDetailsScreen(id: Int, type: NoteType) {
    navigate(route = NoteDetails(id = id, type = type))
}