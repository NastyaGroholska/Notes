package com.ahrokholska.create_note.presentation.fill.screenTypes.guidance

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrokholska.create_note.R
import com.ahrokholska.create_note.presentation.BottomBarSave
import com.ahrokholska.note_presentation.composable.GuidanceImage
import com.ahrokholska.presentation.composable.TopBar
import com.ahrokholska.presentation.theme.background

@Composable
internal fun GuidanceNoteScreen(
    viewModel: GuidanceNoteScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNoteSaved: () -> Unit
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.changeImage(uri.toString())
            }
        }
    GuidanceNoteScreenContent(
        title = viewModel.title.collectAsState().value,
        body = viewModel.body.collectAsState().value,
        image = viewModel.image.collectAsState().value,
        onTitleChange = viewModel::changeTitle,
        onBodyChange = viewModel::changeBody,
        onBackClick = onBackClick,
        onChangeImageClick = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onSaveClick = { title, body, image ->
            viewModel.saveNote(title, body, image, onNoteSaved)
        }
    )
}

@Composable
internal fun GuidanceNoteScreenContent(
    title: String,
    body: String,
    image: String,
    onBackClick: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    onBodyChange: (String) -> Unit = {},
    onChangeImageClick: () -> Unit = {},
    onSaveClick: (title: String, body: String, image: String) -> Unit = { _, _, _ -> },
) {
    val adjustedTitle = title.ifEmpty { stringResource(R.string.guidance_title) }
    val adjustedBody = body.ifEmpty { stringResource(R.string.your_guidance) }
    Scaffold(
        containerColor = background,
        topBar = {
            TopBar(
                modifier = Modifier.statusBarsPadding(),
                onBackClick = onBackClick
            )
        },
        bottomBar = { BottomBarSave { onSaveClick(adjustedTitle, adjustedBody, image) } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Box {
                GuidanceImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .heightIn(max = 260.dp),
                    image = image
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.background)
                        .clickable { onChangeImageClick() }
                        .padding(16.dp),
                    painter = rememberVectorPainter(image = Icons.Outlined.Edit),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = adjustedBody,
                onValueChange = onBodyChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary)
            )
            { innerTextField ->
                innerTextField()
            }
        }
    }
}

@Preview
@Composable
private fun GuidanceNoteScreenPreview() {
    GuidanceNoteScreenContent(
        title = "",
        image = "",
        body = "Create a mobile app UI Kit that provide a basic notes functionality but with some improvement. \n" +
                "\n" +
                "There will be a choice to select what kind of notes that user needed, so the experience while taking notes can be unique based on the needs.",
    )
}