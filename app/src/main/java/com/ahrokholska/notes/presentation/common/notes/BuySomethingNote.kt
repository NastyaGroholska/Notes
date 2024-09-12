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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.theme.BlackAlpha20
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun BuySomethingNote(
    title: String,
    items: List<Pair<Boolean, String>>,
    color: Color,
    shouldShowNoteType: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = contentPadding)
            .width(noteWidth)
            .background(color = color, shape = RoundedCornerShape(noteCornerRadius))
            .border(
                width = 1.dp,
                color = BlackAlpha20,
                shape = RoundedCornerShape(noteCornerRadius)
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = item.first, onCheckedChange = {}, enabled = false)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    text = item.second,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        if (shouldShowNoteType) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BlackAlpha20,
                        shape = RoundedCornerShape(0.dp, 0.dp, noteCornerRadius, noteCornerRadius)
                    )
                    .padding(12.dp),
                text = stringResource(id = NoteType.InterestingIdea.title),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
private fun BuySomethingPreview() {
    BuySomethingNote(
        title = "\uD83D\uDED2 Monthly Needs",
        items = listOf(
            true to "Create a mobile app UI",
            false to "Create a mo\n" +
                    "bile app\n" +
                    " UI",
            true to "Create a mobile app UI",
        ),
        color = noteColors[4]
    )
}