package com.ahrokholska.create_note.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.ahrokholska.create_note.presentation.fill.CreateNoteScreen
import com.ahrokholska.create_note.presentation.type.SelectNoteTypeScreen
import com.ahrokholska.note_presentation.model.NoteType
import kotlinx.serialization.Serializable

@Serializable
data object CreateNewNotesGraph {
    @Serializable
    data object SelectNoteType  //TODO

    @Serializable
    data class CreateNote(val type: NoteType)
}

fun NavGraphBuilder.createNewNotesGraph(
    navController: NavHostController,
    onExit: () -> Unit,
    onNoteSaved: () -> Unit,
) {
    navigation<CreateNewNotesGraph>(startDestination = CreateNewNotesGraph.SelectNoteType) {
        composable<CreateNewNotesGraph.SelectNoteType> {
            SelectNoteTypeScreen(
                onBackClick = onExit,
                onTypeClick = {
                    navController.navigate(CreateNewNotesGraph.CreateNote(it))
                }
            )
        }

        composable<CreateNewNotesGraph.CreateNote> {
            val args = it.toRoute<CreateNewNotesGraph.CreateNote>()
            CreateNoteScreen(
                type = args.type,
                onBackClick = navController::navigateUp,
                onNoteSaved = onNoteSaved
            )
        }
    }
}

fun NavController.navigateToCreateNewNotesGraph(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = CreateNewNotesGraph, builder = builder)
}

fun NavController.popCreateNewNotesGraph() {
    popBackStack(
        route = CreateNewNotesGraph,
        inclusive = true
    )
}

fun NavOptionsBuilder.popUpToCreateNewNotesGraph(inclusive: Boolean = false) =
    popUpTo(CreateNewNotesGraph) {
        this.inclusive = inclusive
    }