package com.ahrokholska.note_details.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun BottomBar(
    isPinned: Boolean,
    isFinished: Boolean,
    onPinClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.End) {
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .clickable(enabled = !isFinished) { onPinClick() }
                    .padding(16.dp),
                imageVector = if (isPinned) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                tint = if (isPinned) MaterialTheme.colorScheme.primary else LocalContentColor.current
            )
            Spacer(Modifier.width(5.dp))
            Icon(
                modifier = Modifier
                    .clickable { onMoreClick() }
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                imageVector = Icons.Filled.MoreHoriz,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun BottomBarNoteDetailsPreview() {
    BottomBar(isPinned = false, isFinished = false)
}