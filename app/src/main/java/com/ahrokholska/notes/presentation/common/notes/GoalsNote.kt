package com.ahrokholska.notes.presentation.common.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.api.model.Note
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.theme.BlackAlpha20
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun GoalsNote(
    modifier: Modifier = Modifier,
    title: String,
    tasks: List<Pair<Note.Goals.Task, List<Note.Goals.Task>>>,
    color: Color,
    onNoteClick: () -> Unit = {},
    shouldShowNoteType: Boolean = true
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(noteCornerRadius))
            .clickable { onNoteClick() }
            .background(color = color, shape = RoundedCornerShape(noteCornerRadius))
            .border(
                width = 1.dp,
                color = BlackAlpha20,
                shape = RoundedCornerShape(noteCornerRadius)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            tasks.forEach { task ->
                Task(
                    finished = task.first.finished,
                    text = task.first.text
                )
                task.second.forEach { subtask ->
                    Task(
                        modifier = Modifier.padding(start = 25.dp),
                        finished = subtask.finished,
                        text = subtask.text
                    )
                }
            }
        }
        if (shouldShowNoteType) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BlackAlpha20,
                        shape = RoundedCornerShape(0.dp, 0.dp, noteCornerRadius, noteCornerRadius)
                    )
                    .padding(12.dp),
                text = stringResource(id = NoteType.Goals.title),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun Task(modifier: Modifier = Modifier, finished: Boolean, text: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
            Checkbox(checked = finished, onCheckedChange = {}, enabled = false)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun GoalsNotePreview() {
    GoalsNote(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = contentPadding)
            .width(noteWidth),
        title = "\uD83D\uDED2 Monthly Needs",
        tasks = listOf(
            Note.Goals.Task(false, "Create a mobile app UI") to listOf(
                Note.Goals.Task(
                    false,
                    "Create a mobile app UI"
                ), Note.Goals.Task(false, "Create")
            ),
            Note.Goals.Task(false, "Create a mobile app UI") to listOf(),
            Note.Goals.Task(false, "Create a mobile app UI") to listOf(
                Note.Goals.Task(
                    false,
                    "Create a mobile app UI"
                )
            ),
        ),
        color = noteColors[4]
    )
}