package com.ahrokholska.notes.presentation.common.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.note_presentation.composable.RoutineTasksSubNote
import com.ahrokholska.note_presentation.model.Note
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.note_presentation.theme.noteColors
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.theme.BlackAlpha20

@Composable
fun RoutineTasksNote(
    modifier: Modifier = Modifier,
    active: List<Note.RoutineTasks.SubNote>,
    completed: List<Note.RoutineTasks.SubNote>,
    color: Color,
    onNoteClick: () -> Unit = {},
    shouldShowNoteType: Boolean = true
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(noteCornerRadius))
            .clickable { onNoteClick() }
            .background(
                color = color.copy(alpha = 0.8f),
                shape = RoundedCornerShape(noteCornerRadius)
            )
            .border(
                width = 1.dp,
                color = BlackAlpha20,
                shape = RoundedCornerShape(noteCornerRadius)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            if (active.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = stringResource(R.string.active_sub_notes),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            active.forEachIndexed { index, item ->
                RoutineTasksSubNote(
                    color = noteColors[index % noteColors.size],
                    title = item.title,
                    text = item.text
                )
                if (index != (active.size - 1)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (completed.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = stringResource(R.string.completed_sub_notes),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            completed.forEachIndexed { index, item ->
                RoutineTasksSubNote(
                    color = noteColors[(index + active.size) % noteColors.size],
                    title = item.title,
                    text = item.text,
                    isCompleted = true
                )
                if (index != (completed.size - 1)) {
                    Spacer(modifier = Modifier.height(16.dp))
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
                text = stringResource(id = NoteType.RoutineTasks.title),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
private fun RoutineTasksNotePreview() {
    RoutineTasksNote(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = contentPadding)
            .width(noteWidth),
        active = listOf(
            Note.RoutineTasks.SubNote(
                title = "Title Here",
                text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement..."
            ),
            Note.RoutineTasks.SubNote(
                title = "Title Here",
                text = "Create a mobile "
            )
        ),
        completed = listOf(
            Note.RoutineTasks.SubNote(
                title = "Title Here",
                text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement..."
            )
        ),
        color = noteColors[4]
    )
}