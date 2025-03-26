package com.ahrokholska.notes.presentation.screens.createNewNotes.type

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.notes.R
import com.ahrokholska.presentation.composable.TopBarWithTitle
import com.ahrokholska.notes.presentation.theme.BlackAlpha40
import com.ahrokholska.presentation.theme.background

@Composable
fun SelectNoteTypeScreen(
    onBackClick: () -> Unit,
    onTypeClick: (NoteType) -> Unit
) {
    SelectNoteTypeScreenContent(onBackClick = onBackClick, onTypeClick = onTypeClick)
}

@Composable
fun SelectNoteTypeScreenContent(
    onBackClick: () -> Unit = {},
    onTypeClick: (NoteType) -> Unit = {}
) {
    Scaffold(
        containerColor = background,
        topBar = {
            TopBarWithTitle(
                modifier = Modifier.statusBarsPadding(),
                title = stringResource(R.string.new_notes),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                stringResource(R.string.what_do_you_want_to_note),
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(32.dp))
            NoteType.entries.forEachIndexed { index, type ->
                Type(
                    title = stringResource(id = type.title),
                    description = stringResource(id = type.description),
                    icon = type.icon,
                    color = type.color,
                    onClick = { onTypeClick(type) }
                )
                if (index < (NoteType.entries.size - 1)) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun Type(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .background(color = BlackAlpha40, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun SelectNoteTypeScreenPreview() {
    SelectNoteTypeScreenContent()
}