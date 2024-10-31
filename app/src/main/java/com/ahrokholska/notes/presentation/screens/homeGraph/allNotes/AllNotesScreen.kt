package com.ahrokholska.notes.presentation.screens.homeGraph.allNotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.NotePreviewCard
import com.ahrokholska.notes.presentation.common.topBar.TopBarWithTitle
import com.ahrokholska.notes.presentation.model.NotePreview
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.theme.background
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun AllNotesScreen(
    viewModel: AllNotesScreenViewModel = hiltViewModel(),
    type: NoteType,
    onBackClick: () -> Unit
) {
    AllNotesScreenContent(
        type = type,
        notes = viewModel.notes.collectAsState().value,
        onBackClick = onBackClick,
        onNoteClick = { _, _ -> }
    )
}

@Composable
fun AllNotesScreenContent(
    type: NoteType,
    notes: List<NotePreview>,
    onBackClick: () -> Unit = {},
    onNoteClick: (Int, NoteType) -> Unit = { _, _ -> }
) {
    Scaffold(
        containerColor = background,
        topBar = {
            TopBarWithTitle(
                modifier = Modifier.statusBarsPadding(),
                title = stringResource(type.title) + " " + stringResource(R.string.notes),
                onBackClick = onBackClick
            )
        },
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            items(notes) { note ->
                NotePreviewCard(
                    note = note,
                    onNoteClick = onNoteClick,
                    shouldShowNoteType = false
                )
            }
        }
    }
}

@Preview
@Composable
private fun AllNotesScreenPreview() {
    AllNotesScreenContent(
        NoteType.InterestingIdea,
        listOf(
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaa\naaa\naaaaa aaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
            NotePreview.InterestingIdea(
                title = "\uD83D\uDCA1 New Product Idea Design aaaaaaaaaaaaaaaaaaa",
                body = "Create a mobile app UI",
                color = noteColors[4]
            ),
        )
    )
}