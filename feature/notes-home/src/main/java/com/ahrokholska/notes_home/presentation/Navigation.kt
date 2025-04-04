package com.ahrokholska.notes_home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.notes_home.presentation.allNotes.AllNotesScreen
import com.ahrokholska.notes_home.presentation.allPinnedNotes.AllPinnedNotesScreen
import com.ahrokholska.notes_home.presentation.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeGraph {
    @Serializable
    internal data object Home

    @Serializable
    internal data class AllNotes(val type: NoteType)

    @Serializable
    internal data object AllPinnedNotes
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    onNoteClick: (Int, NoteType) -> Unit,
    decoration: @Composable (content: @Composable (scrollBottomPadding: Dp) -> Unit) -> Unit =
        @Composable { content -> content(0.dp) }
) {
    navigation<HomeGraph>(startDestination = HomeGraph.Home) {
        composable<HomeGraph.Home> {
            HomeScreen(
                decoration = decoration,
                onNoteClick = onNoteClick,
                onViewAllClick = { navController.navigate(HomeGraph.AllNotes(it)) },
                onViewAllPinnedClick = { navController.navigate(HomeGraph.AllPinnedNotes) }
            )
        }

        composable<HomeGraph.AllNotes> {
            val args = it.toRoute<HomeGraph.AllNotes>()
            AllNotesScreen(
                type = args.type,
                onBackClick = navController::navigateUp,
                onNoteClick = onNoteClick
            )
        }

        composable<HomeGraph.AllPinnedNotes> {
            AllPinnedNotesScreen(
                onBackClick = navController::navigateUp,
                onNoteClick = onNoteClick
            )
        }
    }
}