package com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.routine

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.note_presentation.theme.noteColors
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarSave
import com.ahrokholska.notes.presentation.model.TextAndError
import com.ahrokholska.presentation.composable.TopBar
import com.ahrokholska.presentation.theme.background

@Composable
fun RoutineTasksNoteScreen(
    viewModel: RoutineTasksNoteScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNoteSaved: () -> Unit
) {
    RoutineTasksNoteScreenContent(
        items = viewModel.items.collectAsState().value,
        onAddItemClick = viewModel::addItem,
        onItemTitleChange = viewModel::changeItemTitle,
        onItemBodyChange = viewModel::changeItemBody,
        onBackClick = onBackClick,
        onSaveClick = { viewModel.saveNote(it, onNoteSaved) }
    )
}

@Composable
fun RoutineTasksNoteScreenContent(
    items: List<Pair<TextAndError, TextAndError>>,
    onBackClick: () -> Unit = {},
    onAddItemClick: () -> Unit = {},
    onItemTitleChange: (String, Int) -> Unit = { _, _ -> },
    onItemBodyChange: (String, Int) -> Unit = { _, _ -> },
    onSaveClick: (List<Pair<TextAndError, TextAndError>>) -> Unit = { },
) {
    Scaffold(
        containerColor = background,
        topBar = {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                onBackClick = onBackClick
            )
        },
        bottomBar = { BottomBarSave { onSaveClick(items) } }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(items) { index, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = noteColors[index % noteColors.size],
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = false, onCheckedChange = {})
                        BasicTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = item.first.text.ifEmpty { stringResource(R.string.title) },
                            onValueChange = { onItemTitleChange(it, index) },
                            textStyle = MaterialTheme.typography.titleLarge
                        )
                        { innerTextField ->
                            innerTextField()
                        }
                    }
                    if (item.first.error) {
                        Text(
                            modifier = Modifier.padding(start = 50.dp),
                            text = stringResource(R.string.empty_name),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        value = item.second.text,
                        onValueChange = { onItemBodyChange(it, index) },
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                    { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (item.second.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.enter_item_name),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            innerTextField()
                        }
                    }
                    if (item.second.error) {
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            text = stringResource(R.string.empty_name),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                if (index != (items.size - 1)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { onAddItemClick() },
                    text = stringResource(R.string.add_sub_notes),
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RoutineTasksNoteScreenPreview() {
    RoutineTasksNoteScreenContent(
        items = listOf(
            TextAndError("") to TextAndError(""),
            TextAndError("") to TextAndError(""),
            TextAndError("", true) to TextAndError("", true),
            TextAndError("") to TextAndError(""),
        )
    )
}