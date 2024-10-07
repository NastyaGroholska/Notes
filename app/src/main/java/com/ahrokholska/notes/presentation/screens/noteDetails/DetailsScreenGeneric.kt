package com.ahrokholska.notes.presentation.screens.noteDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarNoteDetails
import com.ahrokholska.notes.presentation.common.topBar.TopBar
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.theme.background

@Composable
fun <T : Note> DetailsScreenGeneric(
    note: T?,
    onBackClick: () -> Unit = {},
    onPinClick: (Boolean) -> Unit = {},
    onMoreClick: () -> Unit = {},
    content: @Composable (PaddingValues, T) -> Unit
) {
    Scaffold(
        containerColor = background,
        topBar = {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomBarNoteDetails(
                isPinned = note?.isPinned ?: false,
                onPinClick = if (note == null) ({}) else ({ onPinClick(note.isPinned) }),
                onMoreClick = if (note == null) ({}) else onMoreClick
            )
        }
    ) { innerPadding ->
        if (note == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            content(innerPadding, note)
        }
    }
}