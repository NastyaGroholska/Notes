package com.ahrokholska.notes.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahrokholska.note_details.presentation.navigation.navigateToNoteDetailsScreen
import com.ahrokholska.note_details.presentation.navigation.noteDetailsScreen
import com.ahrokholska.note_search.presentation.navigateToNoteSearchScreen
import com.ahrokholska.note_search.presentation.noteSearchScreen
import com.ahrokholska.note_search.presentation.popUpToNoteSearchScreen
import com.ahrokholska.notes.presentation.common.bottomBar.BottomAppBar
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.CreateNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.type.SelectNoteTypeScreen
import com.ahrokholska.notes.presentation.screens.finishedNotes.FinishedNotesScreen
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
                    onPlusClick = { navController.navigate(Screen.CreateNewNotesGraph) },
                    onScreenClick = {
                        when (it) {
                            BottomBarScreen.HOME -> {}
                            BottomBarScreen.FINISHED -> navController.navigate(Screen.AllFinishedNotes)
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

        composable<Screen.AllFinishedNotes> {
            FinishedNotesScreen(
                onNoteClick = navController::navigateToNoteDetailsScreen,
                onPlusClick = {
                    navController.navigate(Screen.CreateNewNotesGraph) {
                        popUpTo(Screen.AllFinishedNotes) {
                            inclusive = true
                        }
                    }
                },
                onScreenClick = {
                    when (it) {
                        BottomBarScreen.HOME -> navController.navigateUp()
                        BottomBarScreen.FINISHED -> {}
                        BottomBarScreen.SEARCH -> navController.navigateToNoteSearchScreen {
                            popUpTo(Screen.AllFinishedNotes) {
                                inclusive = true
                            }
                        }

                        BottomBarScreen.SETTINGS -> navController.navigate(Screen.Settings) {
                            popUpTo(Screen.AllFinishedNotes) {
                                inclusive = true
                            }
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.BottomCenter
            ) {
                var bottomBarHeight by remember { mutableStateOf(0.dp) }
                val density = LocalDensity.current

                content(bottomBarHeight)

                BottomAppBar(
                    modifier = Modifier.onGloballyPositioned {
                        bottomBarHeight = with(density) { it.size.height.toDp() }
                    },
                    currentScreen = BottomBarScreen.SEARCH,
                    onPlusClick = {
                        navController.navigate(Screen.CreateNewNotesGraph) {
                            popUpToNoteSearchScreen(inclusive = true)
                        }
                    },
                    onScreenClick = {
                        when (it) {
                            BottomBarScreen.HOME -> navController.navigateUp()
                            BottomBarScreen.FINISHED -> navController.navigate(Screen.AllFinishedNotes) {
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
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onBackClick = navController::navigateUp,
                onPlusClick = {
                    navController.navigate(Screen.CreateNewNotesGraph) {
                        popUpTo(Screen.Settings) {
                            inclusive = true
                        }
                    }
                },
                onScreenClick = {
                    when (it) {
                        BottomBarScreen.HOME -> navController.navigateUp()
                        BottomBarScreen.FINISHED -> navController.navigate(Screen.AllFinishedNotes) {
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