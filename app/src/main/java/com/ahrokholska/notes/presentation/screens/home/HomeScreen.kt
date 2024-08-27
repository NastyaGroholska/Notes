package com.ahrokholska.notes.presentation.screens.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.bottomBar.BottomAppBar
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.theme.BlackAlpha20
import com.ahrokholska.notes.presentation.theme.background
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    HomeScreenContent(
        pinnedNotes = viewModel.pinnedNotes.collectAsState().value,
        otherNotes = viewModel.allNotes.collectAsState().value
    )
}

private val contentPadding = 16.dp
private val noteWidth = 180.dp
private val noteCornerRadius = 8.dp

@Composable
fun HomeScreenContent(
    pinnedNotes: List<Note>,
    otherNotes: List<List<Note>>,
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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                NoteList(
                    title = stringResource(R.string.pinned_notes),
                    notes = pinnedNotes,
                    onViewAllClick = {})
            }
            items(otherNotes) { noteList ->
                if (noteList.isNotEmpty()) {
                    NoteList(
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
fun NoteItem(title: String, text: String, color: Color, type: NoteType? = null) {
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
            Note(
                title = "\uD83D\uDCA1 New Product Idea Design",
                text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n\n" +
                        "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
                type = NoteType.InterestingIdea,
                color = noteColors[0]
            ),
            Note(
                title = "\uD83D\uDCA1 New Product Idea Design",
                text = "Create a mobile app UI",
                type = NoteType.InterestingIdea,
                color = noteColors[3]
            )
        ),
        otherNotes = listOf()
    )
}