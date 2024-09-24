package com.ahrokholska.notes.presentation.screens.noteDetails.buy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarNoteDetails
import com.ahrokholska.notes.presentation.common.topBar.TopBar
import com.ahrokholska.notes.presentation.model.Note
import com.ahrokholska.notes.presentation.theme.background
import com.ahrokholska.notes.presentation.theme.noteColors

@Composable
fun BuyingSomethingDetailsScreen(
    viewModel: BuySomethingDetailsScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    BuyingSomethingDetailsScreenContent(
        viewModel.note.collectAsState().value,
        onItemCheckboxClick = viewModel::changeItemCheck,
        onBackClick = onBackClick
    )
}

@Composable
fun BuyingSomethingDetailsScreenContent(
    note: Note.BuyingSomething?,
    onItemCheckboxClick: (Int, Boolean) -> Unit = { _, _ -> },
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = background,
        topBar = {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomBarNoteDetails(
                isPinned = note?.isPinned ?: false,
                onPinClick = if (note == null) ({}) else ({ TODO() }),
                onMoreClick = if (note == null) ({}) else ({ TODO() })
            )
        }
    ) { innerPadding ->
        if (note == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = note.color)
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Text(text = note.title, style = MaterialTheme.typography.displaySmall)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                itemsIndexed(note.items) { index, item ->
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
}

@Preview
@Composable
private fun InterestingIdeaDetailsScreenPreview() {
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