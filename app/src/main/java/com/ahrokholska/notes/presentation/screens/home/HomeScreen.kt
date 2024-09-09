package com.ahrokholska.notes.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
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
import androidx.compose.ui.graphics.Color
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
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.bottomBar.BottomAppBar
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.notes.presentation.common.notes.InterestingIdeaNote
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.model.Note2
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.theme.BlackAlpha20
import com.ahrokholska.notes.presentation.theme.background
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onPlusClick: () -> Unit,
    onScreenClick: (screen: BottomBarScreen) -> Unit
) {
    HomeScreenContent(
        pinnedNotes = viewModel.pinnedNotes.collectAsState().value,
        interestingIdeaNotes = viewModel.interestingIdeaNotes.collectAsState().value,
        otherNotes = viewModel.allNotes.collectAsState().value,
        onPlusClick = onPlusClick,
        onScreenClick = onScreenClick
    )
}

private val contentPadding = 16.dp
private val noteWidth = 180.dp
private val noteCornerRadius = 8.dp

@Composable
fun HomeScreenContent(
    pinnedNotes: List<Note2>,
    interestingIdeaNotes: List<Note.InterestingIdea>,
    otherNotes: List<List<Note2>>,
    onPlusClick: () -> Unit = {},
    onScreenClick: (screen: BottomBarScreen) -> Unit = {}
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
        if (pinnedNotes.isEmpty() && interestingIdeaNotes.isEmpty() && (otherNotes.isEmpty() || otherNotes.all { it.isEmpty() })) {
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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (pinnedNotes.isNotEmpty()) {
                item {
                    NoteListOld(
                        title = stringResource(R.string.pinned_notes),
                        notes = pinnedNotes,
                        onViewAllClick = {})
                }
            }
            if (interestingIdeaNotes.isNotEmpty()) {
                item {
                    NoteList(
                        title = stringResource(NoteType.InterestingIdea.title),
                        notes = interestingIdeaNotes,
                        onViewAllClick = {})
                }
            }
            items(otherNotes) { noteList ->
                if (noteList.isNotEmpty()) {
                    NoteListOld(
                        title = stringResource(noteList.first().type.title),
                        notes = noteList,
                        shouldShowNoteType = false,
                        onViewAllClick = {})
                }
            }
            item {
                Spacer(modifier = Modifier.height(bottomBarHeight))
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
private fun NoteListOld(
    title: String,
    notes: List<Note2>,
    onViewAllClick: () -> Unit,
    shouldShowNoteType: Boolean = true
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
                NoteItem(
                    title = note.title,
                    text = note.text,
                    color = note.color,
                    type = if (shouldShowNoteType) note.type else null
                )
            }
        }
    }
}

@Composable
private fun NoteList(
    title: String,
    notes: List<Note>,
    onViewAllClick: () -> Unit,
    shouldShowNoteType: Boolean = true
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
                when (note) {
                    is Note.BuyingSomething -> TODO()
                    is Note.Goals -> TODO()
                    is Note.Guidance -> TODO()
                    is Note.InterestingIdea -> InterestingIdeaNote(
                        title = note.title,
                        text = note.body,
                        color = note.color,
                        shouldShowNoteType = shouldShowNoteType
                    )

                    is Note.RoutineTasks -> TODO()
                }
            }
        }
    }
}

@Composable
private fun NoteItem(title: String, text: String, color: Color, type: NoteType? = null) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = contentPadding)
            .width(noteWidth)
            .background(color = color, shape = RoundedCornerShape(noteCornerRadius))
            .border(
                width = 1.dp,
                color = BlackAlpha20,
                shape = RoundedCornerShape(noteCornerRadius)
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        type?.let {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BlackAlpha20,
                        shape = RoundedCornerShape(0.dp, 0.dp, noteCornerRadius, noteCornerRadius)
                    )
                    .padding(12.dp),
                text = stringResource(id = type.title),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        pinnedNotes = listOf(
            Note2(
                title = "\uD83D\uDCA1 New Product Idea Design",
                text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n\n" +
                        "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
                type = NoteType.InterestingIdea,
                color = noteColors[0]
            ),
            Note2(
                title = "\uD83D\uDCA1 New Product Idea Design",
                text = "Create a mobile app UI",
                type = NoteType.InterestingIdea,
                color = noteColors[3]
            )
        ),
        interestingIdeaNotes = listOf(
            Note.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design",
                body = "Create a mobile app UI",
                color = noteColors[4]
            )
        ),
        otherNotes = listOf()
    )
}