package com.ahrokholska.note_search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.api.model.NoteTitle
import com.ahrokholska.api.model.NoteType
import com.ahrokholska.presentation.R
import com.ahrokholska.presentation.theme.background

@Composable
internal fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNoteClick: (Int, NoteType) -> Unit,
    decoration: @Composable (content: @Composable (scrollBottomPadding: Dp) -> Unit) -> Unit =
        @Composable { content -> content(0.dp) },
) {
    decoration { scrollBottomPadding ->
        SearchScreenContent(
            scrollBottomPadding = scrollBottomPadding,
            list = viewModel.titles.collectAsState().value,
            onSearchChange = viewModel::changeSearch,
            onBackClick = onBackClick,
            onNoteClick = onNoteClick
        )
    }
}

@Composable
internal fun SearchScreenContent(
    list: List<Pair<NoteTitle, Pair<Int, Int>>>,
    onSearchChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onNoteClick: (Int, NoteType) -> Unit = { _, _ -> },
    scrollBottomPadding: Dp = 0.dp,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 9.dp, horizontal = 4.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable { onBackClick() }
                    .padding(horizontal = 12.dp)
                    .size(14.dp),
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = null
            )
            var value by rememberSaveable { mutableStateOf("") }
            Box(modifier = Modifier.padding(end = 16.dp)) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        .padding(8.dp),
                    value = value,
                    onValueChange = {
                        value = it
                        onSearchChange(it)
                    },
                    decorationBox = { innerTextField ->
                        innerTextField()
                    }
                )
                if (value.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.search),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = Modifier.padding(horizontal = 6.dp)) {
            items(list) { item ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNoteClick(item.first.id, item.first.type) }
                        .padding(vertical = 12.dp),
                    text = buildAnnotatedString {
                        append(item.first.title)
                        addStyle(
                            style = SpanStyle(background = Color.Yellow),
                            start = item.second.first,
                            end = item.second.second
                        )
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(scrollBottomPadding))
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreenContent(
        list = listOf(
            NoteTitle("Product Idea", 0, NoteType.Goals) to (0 to 1),
            NoteTitle("Monthly Buying List", 0, NoteType.Goals) to (0 to 1)
        )
    )
}