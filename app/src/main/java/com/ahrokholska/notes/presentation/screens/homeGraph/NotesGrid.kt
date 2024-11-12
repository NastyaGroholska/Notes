package com.ahrokholska.notes.presentation.screens.homeGraph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ahrokholska.notes.presentation.common.topBar.TopBarWithTitle
import com.ahrokholska.notes.presentation.theme.background

@Composable
fun NotesGrid(
    topBar: @Composable () -> Unit = {},
    content: LazyStaggeredGridScope.() -> Unit
) {
    Scaffold(
        containerColor = background,
        topBar = topBar,
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            content()
        }
    }
}

@Composable
fun NotesGridSimpleTopBar(
    title: String,
    onBackClick: () -> Unit = {},
    content: LazyStaggeredGridScope.() -> Unit
) {
    NotesGrid(
        topBar = {
            TopBarWithTitle(
                modifier = Modifier.statusBarsPadding(),
                title = title,
                onBackClick = onBackClick
            )
        },
        content = content
    )
}