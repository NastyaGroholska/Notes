package com.ahrokholska.note_details.presentation.noteTypes.buy

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
import com.ahrokholska.note_details.presentation.DetailsScreenGeneric
import com.ahrokholska.note_presentation.model.Note
import com.ahrokholska.note_presentation.theme.noteColors

@Composable
internal fun BuyingSomethingDetailsScreen(
    viewModel: BuySomethingDetailsScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    BuyingSomethingDetailsScreenContent(
        viewModel.note.collectAsState().value,
        onItemCheckboxClick = viewModel::changeItemCheck,
        onBackClick = onBackClick,
        onPinClick = viewModel::pinStatusChangeNote,
        onFinishClick = viewModel::finishNote,
        onDelete = viewModel::deleteNote
    )
}

@Composable
internal fun BuyingSomethingDetailsScreenContent(
    note: Note.BuyingSomething?,
    onItemCheckboxClick: (Int, Boolean) -> Unit = { _, _ -> },
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
            itemsIndexed(noteNotNull.items) { index, item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = item.first,
                        onCheckedChange = { onItemCheckboxClick(index, it) }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = item.second, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview
@Composable
private fun BuyingSomethingDetailsScreenPreview() {
    BuyingSomethingDetailsScreenContent(
        Note.BuyingSomething(
            title = "\uD83D\uDCA1 New Product Ideas",
            items = listOf(
                true to "A box of instant noodles",
                false to "3 boxes of coffee",
                false to "1"
            ),
            color = noteColors[0]
        )
    )
}