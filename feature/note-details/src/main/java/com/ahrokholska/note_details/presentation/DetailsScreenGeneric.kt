package com.ahrokholska.note_details.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahrokholska.dialog.CancelConfirmDialog
import com.ahrokholska.note_details.R
import com.ahrokholska.note_details.presentation.common.BottomBar
import com.ahrokholska.note_presentation.model.Note
import com.ahrokholska.presentation.composable.TopBar
import com.ahrokholska.presentation.theme.background
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <T : Note> DetailsScreenGeneric(
    note: T?,
    onBackClick: () -> Unit = {},
    onPinClick: (Boolean) -> Unit = {},
    onFinishClick: () -> Unit = {},
    onDeleteClick: (onSuccess: () -> Unit) -> Unit = {},
    content: @Composable (PaddingValues, T) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showFinishDialog by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = background,
        topBar = {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomBar(
                isPinned = note?.isPinned ?: false,
                isFinished = note?.isFinished ?: false,
                onPinClick = if (note == null) ({}) else ({ onPinClick(note.isPinned) }),
                onMoreClick = if (note == null) ({}) else ({ showBottomSheet = true })
            )
        }
    ) { innerPadding ->
        if (note == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            content(innerPadding, note)
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(CircleShape)
                            .clickable {
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                            }
                            .background(color = Color.Black.copy(alpha = 0.1f))
                            .padding(4.dp),
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                    if (note?.isFinished != true) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showFinishDialog = true }
                                .padding(vertical = 16.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = stringResource(R.string.mark_as_finished))
                        }
                    }
                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDeleteDialog = true }
                            .padding(vertical = 16.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = stringResource(R.string.delete_note), color = Color.Red)
                    }
                }
            }
        }

        if (showDeleteDialog) {
            CancelConfirmDialog(
                title = stringResource(R.string.delete_note),
                text = stringResource(R.string.are_you_sure_you_want_to_delete_this_note),
                onCancel = { showDeleteDialog = false },
                onConfirm = {
                    onDeleteClick {
                        Toast.makeText(
                            context,
                            context.getString(R.string.note_was_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackClick()
                    }
                }
            )
        }

        if (showFinishDialog) {
            CancelConfirmDialog(
                title = stringResource(R.string.mark_as_finished),
                text = stringResource(R.string.are_you_sure_you_want_to_mark_this_note_as_finished),
                onCancel = { showFinishDialog = false },
                onConfirm = {
                    showFinishDialog = false
                    onFinishClick()
                }
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenGenericPreview() {
    DetailsScreenGeneric<Note>(note = null) { _, _ -> }
}