package com.ahrokholska.notes.presentation.common.bottomBar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ahrokholska.presentation.R

enum class BottomBarScreen(
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val text: Int
) {
    HOME(Icons.Outlined.Home, Icons.Filled.Home, R.string.home),
    FINISHED(Icons.Outlined.AssignmentTurnedIn, Icons.Filled.AssignmentTurnedIn, R.string.finished),
    SEARCH(Icons.Outlined.Search, Icons.Filled.Search, R.string.search),
    SETTINGS(Icons.Outlined.Settings, Icons.Filled.Settings, R.string.settings)
}