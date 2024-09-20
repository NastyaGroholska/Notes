package com.ahrokholska.notes.presentation.common.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.notes.R
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.theme.BlackAlpha20
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun RoutineTasksNote(
    active: List<Note.RoutineTasks.SubNote>,
    completed: List<Note.RoutineTasks.SubNote>,
    color: Color,
    shouldShowNoteType: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = contentPadding)
            .width(noteWidth)
            .background(color = color.copy(alpha = 0.8f), shape = RoundedCornerShape(noteCornerRadius))
            .border(
                width = 1.dp,
                color = BlackAlpha20,
                shape = RoundedCornerShape(noteCornerRadius)
            )
    ) {
        Column(
            modifier = Modifier.padding(12.dp).weight(1f)
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
                SubNote(
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
                SubNote(
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

@Composable
private fun SubNote(color: Color, title: String, text: String, isCompleted: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isCompleted, onCheckedChange = {}, enabled = false)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
                fontWeight = FontWeight.Bold
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun RoutineTasksNotePreview() {
    RoutineTasksNote(
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