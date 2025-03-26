package com.ahrokholska.notes.presentation.screens.homeGraph.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.note_presentation.model.NotePreview
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.note_presentation.theme.noteColors
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.NotePreviewCard
import com.ahrokholska.notes.presentation.common.bottomBar.BottomAppBar
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.notes.presentation.common.notes.noteWidth
import com.ahrokholska.presentation.theme.background

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onPlusClick: () -> Unit,
    onScreenClick: (screen: BottomBarScreen) -> Unit,
    onNoteClick: (Int, NoteType) -> Unit,
    onViewAllClick: (NoteType) -> Unit,
    onViewAllPinnedClick: () -> Unit,
) {
    HomeScreenContent(
        pinnedNotes = viewModel.pinnedNotes.collectAsState().value,
        interestingIdeaNotes = viewModel.interestingIdeaNotes.collectAsState().value,
        buySomethingNotes = viewModel.buySomethingNotes.collectAsState().value,
        goalsNotes = viewModel.goalsNotes.collectAsState().value,
        guidanceNotes = viewModel.guidanceNotes.collectAsState().value,
        routineTasksNotes = viewModel.routineTasksNotes.collectAsState().value,
        onPlusClick = onPlusClick,
        onScreenClick = onScreenClick,
        onNoteClick = onNoteClick,
        onViewAllClick = onViewAllClick,
        onViewAllPinnedClick = onViewAllPinnedClick,
    )
}

private val contentPadding = 16.dp

@Composable
fun HomeScreenContent(
    pinnedNotes: List<NotePreview>?,
    interestingIdeaNotes: List<NotePreview.InterestingIdea>?,
    buySomethingNotes: List<NotePreview.BuyingSomething>?,
    goalsNotes: List<NotePreview.Goals>?,
    guidanceNotes: List<NotePreview.Guidance>?,
    routineTasksNotes: List<NotePreview.RoutineTasks>?,
    onPlusClick: () -> Unit = {},
    onScreenClick: (screen: BottomBarScreen) -> Unit = {},
    onNoteClick: (Int, NoteType) -> Unit = { _, _ -> },
    onViewAllClick: (NoteType) -> Unit = {},
    onViewAllPinnedClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(color = background)
            .statusBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        var bottomBarHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        if (pinnedNotes?.isEmpty() == true && interestingIdeaNotes?.isEmpty() == true &&
            buySomethingNotes?.isEmpty() == true && goalsNotes?.isEmpty() == true &&
            guidanceNotes?.isEmpty() == true && routineTasksNotes?.isEmpty() == true
        ) {
            NoData()
        } else if (pinnedNotes.isNullOrEmpty() && interestingIdeaNotes.isNullOrEmpty() &&
            buySomethingNotes.isNullOrEmpty() && goalsNotes.isNullOrEmpty() &&
            guidanceNotes.isNullOrEmpty() && routineTasksNotes.isNullOrEmpty()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (pinnedNotes?.isNotEmpty() == true) {
                    item {
                        NoteList(
                            title = stringResource(R.string.pinned_notes),
                            notes = pinnedNotes,
                            onNoteClick = onNoteClick,
                            onViewAllClick = onViewAllPinnedClick,
                            shouldShowNoteType = true
                        )
                    }
                }
                if (interestingIdeaNotes?.isNotEmpty() == true) {
                    item {
                        NoteList(
                            title = stringResource(NoteType.InterestingIdea.title),
                            notes = interestingIdeaNotes,
                            onNoteClick = onNoteClick,
                            onViewAllClick = { onViewAllClick(NoteType.InterestingIdea) }
                        )
                    }
                }
                if (buySomethingNotes?.isNotEmpty() == true) {
                    item {
                        NoteList(
                            title = stringResource(NoteType.BuyingSomething.title),
                            notes = buySomethingNotes,
                            onNoteClick = onNoteClick,
                            onViewAllClick = { onViewAllClick(NoteType.BuyingSomething) }
                        )
                    }
                }
                if (goalsNotes?.isNotEmpty() == true) {
                    item {
                        NoteList(
                            title = stringResource(NoteType.Goals.title),
                            notes = goalsNotes,
                            onNoteClick = onNoteClick,
                            onViewAllClick = { onViewAllClick(NoteType.Goals) }
                        )
                    }
                }
                if (guidanceNotes?.isNotEmpty() == true) {
                    item {
                        NoteList(
                            title = stringResource(NoteType.Guidance.title),
                            notes = guidanceNotes,
                            onNoteClick = onNoteClick,
                            onViewAllClick = { onViewAllClick(NoteType.Guidance) }
                        )
                    }
                }
                if (routineTasksNotes?.isNotEmpty() == true) {
                    item {
                        NoteList(
                            title = stringResource(NoteType.RoutineTasks.title),
                            notes = routineTasksNotes,
                            onNoteClick = onNoteClick,
                            onViewAllClick = { onViewAllClick(NoteType.RoutineTasks) }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(bottomBarHeight))
                }
            }
        }
        BottomAppBar(
            modifier = Modifier.onGloballyPositioned {
                bottomBarHeight = with(density) { it.size.height.toDp() }
            },
            currentScreen = BottomBarScreen.HOME,
            onPlusClick = onPlusClick,
            onScreenClick = onScreenClick
        )
    }
}

@Composable
private fun NoData() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        Image(
            painter = painterResource(R.drawable.illustration_go),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.start_your_journey),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.notes_your_first_idea_and_start),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(21.dp))
        Icon(imageVector = Icons.Filled.ArrowDownward, contentDescription = null)
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
private fun NoteList(
    title: String,
    notes: List<NotePreview>,
    onNoteClick: (Int, NoteType) -> Unit,
    onViewAllClick: () -> Unit,
    shouldShowNoteType: Boolean = false
) {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = contentPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable { onViewAllClick() },
                text = stringResource(R.string.view_all),
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
        ) {
            notes.forEach { note ->
                NotePreviewCard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = com.ahrokholska.notes.presentation.common.notes.contentPadding)
                        .width(noteWidth),
                    note = note,
                    onNoteClick = onNoteClick,
                    shouldShowNoteType = shouldShowNoteType
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        pinnedNotes = listOf(
            NotePreview.Guidance(
                title = "\uD83D\uDCA1 New Product Idea Design",
                body = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n\n" +
                        "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
                image = "",
                color = noteColors[0]
            ),
            NotePreview.Guidance(
                title = "\uD83D\uDCA1 New Product Idea Design",
                body = "Create a mobile app UI",
                image = "",
                color = noteColors[3]
            )
        ),
        interestingIdeaNotes = listOf(
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design",
                body = "Create a mobile app UI",
                color = noteColors[4]
            )
        ),
        buySomethingNotes = listOf(),
        goalsNotes = listOf(),
        guidanceNotes = listOf(),
        routineTasksNotes = listOf(),
    )
}