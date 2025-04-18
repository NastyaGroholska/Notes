package com.ahrokholska.note_details.presentation.noteTypes.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.note_details.presentation.DetailsScreenGeneric
import com.ahrokholska.note_presentation.R.string.active_sub_notes
import com.ahrokholska.note_presentation.R.string.completed_sub_notes
import com.ahrokholska.note_presentation.composable.RoutineTasksSubNote
import com.ahrokholska.note_presentation.model.Note
import com.ahrokholska.note_presentation.theme.noteColors

@Composable
internal fun RoutineTasksDetailsScreen(
    viewModel: RoutineTasksDetailsScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    RoutineTasksDetailsScreenContent(
        note = viewModel.note.collectAsState().value,
        onBackClick = onBackClick,
        onSubNoteFinish = viewModel::finishSubNote,
        onSubNoteUnFinish = viewModel::unFinishSubNote,
        onPinClick = viewModel::pinStatusChangeNote,
        onFinishClick = viewModel::finishNote,
        onDelete = viewModel::deleteNote
    )
}

@Composable
internal fun RoutineTasksDetailsScreenContent(
    note: Note.RoutineTasks?,
    onBackClick: () -> Unit = {},
    onSubNoteFinish: (Int) -> Unit = {},
    onSubNoteUnFinish: (Int) -> Unit = {},
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
                .background(color = noteNotNull.color.copy(alpha = 0.8f))
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            if (noteNotNull.active.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(bottom = 12.dp),
                        text = stringResource(active_sub_notes),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            itemsIndexed(noteNotNull.active, key = { _, item -> item.id }) { index, subNote ->
                RoutineTasksSubNote(
                    color = noteColors[index % noteColors.size],
                    title = subNote.title,
                    text = subNote.text,
                    clickable = true,
                    onCheckedChange = { onSubNoteFinish(subNote.id) },
                    titleStyle = MaterialTheme.typography.titleLarge,
                    textStyle = MaterialTheme.typography.bodyLarge
                )
                if (index != (noteNotNull.active.size - 1)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (noteNotNull.completed.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = stringResource(completed_sub_notes),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            itemsIndexed(noteNotNull.completed, key = { _, item -> item.id }) { index, subNote ->
                RoutineTasksSubNote(
                    color = noteColors[(index + noteNotNull.active.size) % noteColors.size],
                    title = subNote.title,
                    text = subNote.text,
                    clickable = true,
                    onCheckedChange = { onSubNoteUnFinish(subNote.id) },
                    titleStyle = MaterialTheme.typography.titleLarge,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    isCompleted = true
                )
                if (index != (noteNotNull.completed.size - 1)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun RoutineTasksDetailsScreenPreview() {
    RoutineTasksDetailsScreenContent(
        Note.RoutineTasks(
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
            color = noteColors[0]
        )
    )
}