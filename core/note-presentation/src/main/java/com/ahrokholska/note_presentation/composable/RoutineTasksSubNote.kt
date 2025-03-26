package com.ahrokholska.note_presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.note_presentation.theme.noteColors

@Composable
fun RoutineTasksSubNote(
    color: Color,
    title: String,
    text: String,
    clickable: Boolean = false,
    isCompleted: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall
) {
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
            Checkbox(checked = isCompleted, onCheckedChange = onCheckedChange, enabled = clickable)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = titleStyle,
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
            style = textStyle,
            textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun RoutineTasksSubNotePreview() {
    RoutineTasksSubNote(
        color = noteColors[0],
        title = "Title Here",
        text = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement...",
        isCompleted = false
    )
}