package com.ahrokholska.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahrokholska.create_note.presentation.createNewNotesGraph
import com.ahrokholska.create_note.presentation.navigateToCreateNewNotesGraph
import com.ahrokholska.create_note.presentation.popCreateNewNotesGraph
import com.ahrokholska.finished_notes.presentation.navigateToAllFinishedNotesScreen
import com.ahrokholska.finished_notes.presentation.noteAllFinishedNotesScreen
import com.ahrokholska.finished_notes.presentation.popUpToAllFinishedNotesScreen
import com.ahrokholska.note_details.presentation.navigation.navigateToNoteDetailsScreen
import com.ahrokholska.note_details.presentation.navigation.noteDetailsScreen
import com.ahrokholska.note_search.presentation.navigateToNoteSearchScreen
import com.ahrokholska.note_search.presentation.noteSearchScreen
import com.ahrokholska.note_search.presentation.popUpToNoteSearchScreen
import com.ahrokholska.notes.presentation.common.MainScreenDecoration
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.notes.presentation.screens.homeGraph.allNotes.AllNotesScreen
import com.ahrokholska.notes.presentation.screens.homeGraph.allPinnedNotes.AllPinnedNotesScreen
import com.ahrokholska.notes.presentation.screens.homeGraph.home.HomeScreen
import com.ahrokholska.notes.presentation.screens.settings.SettingsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeGraph) {
        navigation<Screen.HomeGraph>(startDestination = Screen.HomeGraph.Home) {
            composable<Screen.HomeGraph.Home> {
                HomeScreen(
                    onPlusClick = navController::navigateToCreateNewNotesGraph,
                    onScreenClick = {
                        when (it) {
                            BottomBarScreen.HOME -> {}
                            BottomBarScreen.FINISHED -> navController.navigateToAllFinishedNotesScreen()
                            BottomBarScreen.SEARCH -> navController.navigateToNoteSearchScreen()
                            BottomBarScreen.SETTINGS -> navController.navigate(Screen.Settings)
                        }
                    },
                    onNoteClick = navController::navigateToNoteDetailsScreen,
                    onViewAllClick = { navController.navigate(Screen.HomeGraph.AllNotes(it)) },
                    onViewAllPinnedClick = { navController.navigate(Screen.HomeGraph.AllPinnedNotes) }
                )
            }

            composable<Screen.HomeGraph.AllNotes> {
                val args = it.toRoute<Screen.HomeGraph.AllNotes>()
                AllNotesScreen(
                    type = args.type,
                    onBackClick = navController::navigateUp,
                    onNoteClick = navController::navigateToNoteDetailsScreen
                )
            }

            composable<Screen.HomeGraph.AllPinnedNotes> {
                AllPinnedNotesScreen(
                    onBackClick = navController::navigateUp,
                    onNoteClick = navController::navigateToNoteDetailsScreen
                )
            }
        }

        noteAllFinishedNotesScreen(onNoteClick = navController::navigateToNoteDetailsScreen) { content ->
            MainScreenDecoration(
                content = content,
                onPlusClick = {
                    navController.navigateToCreateNewNotesGraph {
                        popUpToAllFinishedNotesScreen(inclusive = true)
                    }
                },
                onScreenClick = {
                    when (it) {
                        BottomBarScreen.HOME -> navController.navigateUp()
                        BottomBarScreen.FINISHED -> {}
                        BottomBarScreen.SEARCH -> navController.navigateToNoteSearchScreen {
                            popUpToAllFinishedNotesScreen(inclusive = true)
                        }

                        BottomBarScreen.SETTINGS -> navController.navigate(Screen.Settings) {
                            popUpToAllFinishedNotesScreen(inclusive = true)
                        }
                    }
                },
            )
        }

        noteDetailsScreen(onExit = navController::navigateUp)

        noteSearchScreen(
            onExit = navController::navigateUp,
            onNoteClick = navController::navigateToNoteDetailsScreen,
        ) { content ->
            MainScreenDecoration(
                content = content,
                onPlusClick = {
                    navController.navigateToCreateNewNotesGraph {
                        popUpToNoteSearchScreen(inclusive = true)
                    }
                },
                onScreenClick = {
                    when (it) {
                        BottomBarScreen.HOME -> navController.navigateUp()
                        BottomBarScreen.FINISHED -> navController.navigateToAllFinishedNotesScreen {
                            popUpToNoteSearchScreen(inclusive = true)
                        }

                        BottomBarScreen.SEARCH -> {}
                        BottomBarScreen.SETTINGS -> navController.navigate(Screen.Settings) {
                            popUpToNoteSearchScreen(inclusive = true)
                        }
                    }
                },
            )
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onBackClick = navController::navigateUp,
                onPlusClick = {
                    navController.navigateToCreateNewNotesGraph {
                        popUpTo(Screen.Settings) {
                            inclusive = true
                        }
                    }
                },
                onScreenClick = {
                    when (it) {
                        BottomBarScreen.HOME -> navController.navigateUp()
                        BottomBarScreen.FINISHED -> navController.navigateToAllFinishedNotesScreen {
                            popUpTo(Screen.Settings) {
                                inclusive = true
                            }
                        }

                        BottomBarScreen.SEARCH -> navController.navigateToNoteSearchScreen {
                            popUpTo(Screen.Settings) {
                                inclusive = true
                            }
                        }

                        BottomBarScreen.SETTINGS -> {}
                    }
                },
            )
        }

        createNewNotesGraph(
            navController = navController,
            onExit = navController::navigateUp,
            onNoteSaved = navController::popCreateNewNotesGraph
        )
    }
}