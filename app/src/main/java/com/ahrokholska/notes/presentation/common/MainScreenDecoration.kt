package com.ahrokholska.notes.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ahrokholska.notes.presentation.common.bottomBar.BottomAppBar
import com.ahrokholska.notes.presentation.common.bottomBar.BottomBarScreen

@Composable
fun MainScreenDecoration(
    currentScreen: BottomBarScreen,
    onPlusClick: () -> Unit = {},
    onScreenClick: (screen: BottomBarScreen) -> Unit = {},
    content: @Composable (scrollBottomPadding: Dp) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        var bottomBarHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        content(bottomBarHeight)

        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned {
                    bottomBarHeight = with(density) { it.size.height.toDp() }
                },
            currentScreen = currentScreen,
            onPlusClick = onPlusClick,
            onScreenClick = onScreenClick,
        )
    }
}

@Preview
@Composable
private fun MainScreenDecorationPreview() {
    MainScreenDecoration(currentScreen = BottomBarScreen.SEARCH,) {
        Text("Content")
    }
}