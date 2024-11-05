package com.ahrokholska.notes.presentation.screens.homeGraph.allPinnedNotes

import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.NotePreviewCard
import com.ahrokholska.notes.presentation.model.NotePreview
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.screens.homeGraph.NotesGrid
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun AllPinnedNotesScreen(
    viewModel: AllPinnedNotesScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNoteClick: (Int, NoteType) -> Unit
) {
    AllPinnedNotesScreenContent(
        notes = listOf(),
        onBackClick = onBackClick,
        onNoteClick = onNoteClick
    )
}

@Composable
fun AllPinnedNotesScreenContent(
    notes: List<NotePreview>,
    onBackClick: () -> Unit = {},
    onNoteClick: (Int, NoteType) -> Unit = { _, _ -> }
) {
    NotesGrid(
        title = stringResource(R.string.pinned_notes),
        onBackClick = onBackClick
    ) {
        items(notes) { note ->
            NotePreviewCard(
                note = note,
                onNoteClick = onNoteClick,
                shouldShowNoteType = true
            )
        }
    }
}

@Preview
@Composable
private fun AllPinnedNotesScreenPreview() {
    AllPinnedNotesScreenContent(
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