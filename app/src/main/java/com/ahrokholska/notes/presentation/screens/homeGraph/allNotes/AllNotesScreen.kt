package com.ahrokholska.notes.presentation.screens.homeGraph.allNotes

import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.NotePreviewCard
import com.ahrokholska.notes.presentation.model.NotePreview
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.screens.homeGraph.NotesGridSimpleTopBar
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun AllNotesScreen(
    viewModel: AllNotesScreenViewModel = hiltViewModel(),
    type: NoteType,
    onBackClick: () -> Unit,
    onNoteClick: (Int, NoteType) -> Unit
) {
    AllNotesScreenContent(
        type = type,
        notes = viewModel.notes.collectAsState().value,
        onBackClick = onBackClick,
        onNoteClick = onNoteClick
    )
}

@Composable
fun AllNotesScreenContent(
    type: NoteType,
    notes: List<NotePreview>,
    onBackClick: () -> Unit = {},
    onNoteClick: (Int, NoteType) -> Unit = { _, _ -> }
) {
    NotesGridSimpleTopBar(
        title = stringResource(type.title) + " " + stringResource(R.string.notes),
        onBackClick = onBackClick
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