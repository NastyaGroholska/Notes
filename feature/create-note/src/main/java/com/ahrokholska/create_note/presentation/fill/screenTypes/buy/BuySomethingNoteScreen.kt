package com.ahrokholska.create_note.presentation.fill.screenTypes.buy

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
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
import com.ahrokholska.create_note.R
import com.ahrokholska.create_note.presentation.BottomBarSave
import com.ahrokholska.create_note.presentation.fill.screenTypes.TextAndError
import com.ahrokholska.presentation.composable.TopBar
import com.ahrokholska.presentation.theme.background

@Composable
internal fun BuySomethingNoteScreen(
    viewModel: BuySomethingNoteScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNoteSaved: () -> Unit
) {
    BuySomethingNoteScreenContent(
        title = viewModel.title.collectAsState().value,
        items = viewModel.items.collectAsState().value,
        onAddItemClick = viewModel::addItem,
        onTitleChange = viewModel::changeTitle,
        onItemChange = viewModel::changeItem,
        onBackClick = onBackClick,
        onSaveClick = { title ->
            viewModel.saveNote(title, onNoteSaved)
        }
    )
}

@Composable
internal fun BuySomethingNoteScreenContent(
    title: String,
    items: List<TextAndError>,
    onBackClick: () -> Unit = {},
    onAddItemClick: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onItemChange: (String, Int) -> Unit = { _, _ -> },
    onSaveClick: (String) -> Unit = {}
) {
    val adjustedTitle = title.ifEmpty { stringResource(R.string.monthly_needs) }
    Scaffold(
        containerColor = background,
        topBar = {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                onBackClick = onBackClick
            )
        },
        bottomBar = { BottomBarSave { onSaveClick(adjustedTitle) } }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            item {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = adjustedTitle,
                    onValueChange = onTitleChange,
                    textStyle = MaterialTheme.typography.displaySmall
                )
                { innerTextField ->
                    innerTextField()
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            itemsIndexed(items) { index, item ->
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = false, onCheckedChange = {})
                        Spacer(modifier = Modifier.width(12.dp))
                        BasicTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = item.text,
                            onValueChange = { onItemChange(it, index) },
                            textStyle = MaterialTheme.typography.bodyLarge
                        )
                        { innerTextField ->
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (item.text.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.enter_item_name),
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                innerTextField()
                            }
                        }
                    }
                    if (item.error)
                        Text(
                            modifier = Modifier.padding(start = 60.dp),
                            text = stringResource(R.string.empty_name),
                            color = MaterialTheme.colorScheme.error
                        )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { onAddItemClick() },
                    text = stringResource(R.string.add_checkbox),
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}

@Preview
@Composable
private fun BuySomethingNoteScreenPreview() {
    BuySomethingNoteScreenContent(
        title = "",
        items = listOf(
            TextAndError("A box of instant noodles", false),
            TextAndError("3 boxes of coffee", false),
            TextAndError("", true)
        )
    )
}