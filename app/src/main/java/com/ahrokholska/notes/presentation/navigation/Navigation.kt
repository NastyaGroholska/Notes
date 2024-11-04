package com.ahrokholska.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.CreateNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.type.SelectNoteTypeScreen
import com.ahrokholska.notes.presentation.screens.homeGraph.allNotes.AllNotesScreen
import com.ahrokholska.notes.presentation.screens.homeGraph.home.HomeScreen
import com.ahrokholska.notes.presentation.screens.noteDetails.NoteDetailsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeGraph) {
        navigation<Screen.HomeGraph>(startDestination = Screen.HomeGraph.Home) {
            composable<Screen.HomeGraph.Home> {
                HomeScreen(
                    onPlusClick = { navController.navigate(Screen.CreateNewNotesGraph) },
                    onScreenClick = {
                        when (it) {
                            BottomBarScreen.HOME -> {}
                            BottomBarScreen.FINISHED -> TODO()
                            BottomBarScreen.SEARCH -> TODO()
                            BottomBarScreen.SETTINGS -> TODO()
                        }
                    },
                    onNoteClick = { id, type ->
                        navController.navigate(Screen.NoteDetails(id, type))
                    },
                    onViewAllClick = { navController.navigate(Screen.HomeGraph.AllNotes(it)) }
                )
            }

            composable<Screen.HomeGraph.AllNotes> {
                val args = it.toRoute<Screen.HomeGraph.AllNotes>()
                AllNotesScreen(
                    type = args.type,
                    onBackClick = navController::navigateUp,
                    onNoteClick = { id, type ->
                        navController.navigate(Screen.NoteDetails(id, type))
                    }
                )
            }

            composable<Screen.HomeGraph.AllPinnedNotes> {
                //TODO
            }
        }

        composable<Screen.NoteDetails> {
            val args = it.toRoute<Screen.NoteDetails>()
            NoteDetailsScreen(
                type = args.type,
                onBackClick = navController::navigateUp
            )
        }

        navigation<Screen.CreateNewNotesGraph>(startDestination = Screen.CreateNewNotesGraph.SelectNoteType) {
            composable<Screen.CreateNewNotesGraph.SelectNoteType> {
                SelectNoteTypeScreen(
                    onBackClick = navController::navigateUp,
                    onTypeClick = {
                        navController.navigate(Screen.CreateNewNotesGraph.CreateNote(it))
                    }
                )
            }

            composable<Screen.CreateNewNotesGraph.CreateNote> {
                val args = it.toRoute<Screen.CreateNewNotesGraph.CreateNote>()
                CreateNoteScreen(
                    type = args.type,
                    onBackClick = navController::navigateUp,
                    onNoteSaved = {
                        navController.popBackStack(
                            route = Screen.CreateNewNotesGraph,
                            inclusive = true
                        )
                    })
            }
        }
    }
}