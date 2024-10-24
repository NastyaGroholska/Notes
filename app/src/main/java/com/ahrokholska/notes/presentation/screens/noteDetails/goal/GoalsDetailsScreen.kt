package com.ahrokholska.notes.presentation.screens.noteDetails.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.domain.model.Note.Goals.Task
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.screens.noteDetails.DetailsScreenGeneric
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun GoalsDetailsScreen(
    viewModel: GoalsDetailsScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    GoalsDetailsScreenContent(
        note = viewModel.note.collectAsState().value,
        onTaskCheckboxClick = viewModel::changeTaskCheck,
        onSubTaskCheckboxClick = viewModel::changeSubtaskCheck,
        onBackClick = onBackClick,
        onPinClick = viewModel::pinStatusChangeNote,
        onFinishClick = viewModel::finishNote,
        onDelete = viewModel::deleteNote
    )
}

@Composable
fun GoalsDetailsScreenContent(
    note: Note.Goals?,
    onTaskCheckboxClick: (Boolean, Int) -> Unit = { _, _ -> },
    onSubTaskCheckboxClick: (Boolean, Int, Int) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {},
    onPinClick: (Boolean) -> Unit = {},
    onFinishClick: () -> Unit = {},
    onDelete: (onSuccess: () -> Unit) -> Unit = {},
) {
    DetailsScreenGeneric(
        note = note,
        onBackClick = onBackClick,
        onPinClick = onPinClick,
        onFinishClick = onFinishClick,
        onDeleteClick = onDelete,
    ) { innerPadding, noteNotNull ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = noteNotNull.color)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(text = noteNotNull.title, style = MaterialTheme.typography.displaySmall)
                Spacer(modifier = Modifier.height(16.dp))
            }
            noteNotNull.tasks.forEachIndexed { index1, pair ->
                item {
                    TaskUI(
                        checked = pair.first.finished,
                        onCheckedChange = { onTaskCheckboxClick(it, index1) },
                        text = pair.first.text
                    )
                }
                itemsIndexed(pair.second) { index2, subtask ->
                    TaskUI(
                        modifier = Modifier.padding(start = 36.dp),
                        checked = subtask.finished,
                        onCheckedChange = { onSubTaskCheckboxClick(it, index1, index2) },
                        text = subtask.text
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskUI(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun GoalsDetailsScreenPreview() {
    GoalsDetailsScreenContent(
        Note.Goals(
            title = "\uD83D\uDCA1 New Product Ideas",
            tasks = listOf(
                Task(false, "A box of instant noodles") to listOf(
                    Task(true, "A box of instant noodles"),
                    Task(false, "A box of instant noodles")
                ),
                Task(true, "A box of instant noodles") to listOf(Task(false, "3 boxes of coffee")),
                Task(false, "A box of instant noodles") to listOf(Task(false, "1"))
            ),
            color = noteColors[0]
        )
    )
}