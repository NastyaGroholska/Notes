package com.ahrokholska.notes.presentation.screens.noteDetails.guidance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.presentation.common.notes.GuidanceImage
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.screens.noteDetails.DetailsScreenGeneric
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun GuidanceDetailsScreen(
    viewModel: GuidanceDetailsScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    GuidanceDetailsScreenContent(
        viewModel.note.collectAsState().value,
        onBackClick = onBackClick
    )
}

@Composable
fun GuidanceDetailsScreenContent(note: Note.Guidance?, onBackClick: () -> Unit = {}) {
    DetailsScreenGeneric(
        note = note,
        onBackClick = onBackClick,
        onPinClick = { TODO() },
        onMoreClick = { TODO() },
    ) { innerPadding, noteNotNull ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = noteNotNull.color)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = noteNotNull.title, style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(16.dp))
            GuidanceImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .heightIn(max = 260.dp),
                image = noteNotNull.image
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = noteNotNull.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
private fun GuidanceDetailsScreenPreview() {
    GuidanceDetailsScreenContent(
        Note.Guidance(
            title = "\uD83D\uDCA1 New Product Ideas",
            body = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n" +
                    "\n" +
                    "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
            image = "",
            color = noteColors[0]
        )
    )
}