package com.ahrokholska.notes.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.TextFormat
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahrokholska.notes.R
import com.ahrokholska.notes.presentation.common.bottomBar.BottomAppBar
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen
import com.ahrokholska.presentation.composable.TopBarWithTitle
import com.ahrokholska.presentation.theme.background

@Composable
fun SettingsScreen(
    onPlusClick: () -> Unit,
    onScreenClick: (screen: BottomBarScreen) -> Unit,
    onBackClick: () -> Unit
) {
    SettingsScreenContent(
        image = "",
        name = "",
        email = "",
        onPlusClick = onPlusClick,
        onScreenClick = onScreenClick,
        onBackClick = onBackClick
    )
}

@Composable
fun SettingsScreenContent(
    image: String,
    name: String,
    email: String,
    onPlusClick: () -> Unit = {},
    onScreenClick: (screen: BottomBarScreen) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(color = background)
            .statusBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        var bottomBarHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        Scaffold(
            containerColor = background,
            topBar = {
                TopBarWithTitle(
                    modifier = Modifier.statusBarsPadding(),
                    title = stringResource(R.string.settings),
                    onBackClick = onBackClick
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(top = 24.dp, bottom = bottomBarHeight)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val imageMod = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondary)
                    if (image.isEmpty()) {
                        Icon(
                            modifier = imageMod,
                            painter = rememberVectorPainter(Icons.Filled.Person),
                            contentDescription = null
                        )
                    } else {
                        SubcomposeAsyncImage(
                            modifier = imageMod,
                            model = image,
                            loading = { CircularProgressIndicator() },
                            error = {
                                Icon(
                                    painter = rememberVectorPainter(image = Icons.Filled.Error),
                                    contentDescription = null
                                )
                            },
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = name, style = MaterialTheme.typography.titleLarge)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = rememberVectorPainter(image = Icons.Outlined.Mail),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = email, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.primary
                        )
                        .clip(RoundedCornerShape(50))
                        .clickable { }
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = rememberVectorPainter(image = Icons.Outlined.Edit),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.edit_profile),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))
                Text(
                    text = stringResource(R.string.app_settings),
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = rememberVectorPainter(image = Icons.Outlined.Lock),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                        text = stringResource(R.string.change_password),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Outlined.ArrowForwardIos),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = rememberVectorPainter(image = Icons.Outlined.TextFormat),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                        text = stringResource(R.string.text_size),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(R.string.medium),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = rememberVectorPainter(image = Icons.Outlined.Notifications),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                        text = stringResource(R.string.notifications),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(R.string.all_active),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Outlined.Logout),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                        text = stringResource(R.string.log_out),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        BottomAppBar(
            modifier = Modifier.onGloballyPositioned {
                bottomBarHeight = with(density) { it.size.height.toDp() }
            },
            currentScreen = BottomBarScreen.SETTINGS,
            onPlusClick = onPlusClick,
            onScreenClick = onScreenClick
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreenContent(
        image = "",
        name = "Michael Antonio",
        email = "anto_michael@gmail.com",
    )
}