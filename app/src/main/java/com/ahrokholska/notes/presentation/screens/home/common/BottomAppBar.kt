package com.ahrokholska.notes.presentation.screens.home.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val plusIconSize: Dp = 32.dp
private val plusPadding: Dp = 16.dp
private val plusSize = plusIconSize + plusPadding * 2
private val plusClearance = 8.dp

@Composable
fun BottomAppBar(
    currentScreen: BottomBarScreen? = null,
    onPlusClick: () -> Unit = {},
    onScreenClick: (screen: BottomBarScreen) -> Unit = {}
) {
    val density = LocalDensity.current
    val colorScheme = MaterialTheme.colorScheme
    val plusSizePx by remember { derivedStateOf { with(density) { plusSize.toPx() } } }
    val plusClearancePx by remember { derivedStateOf { with(density) { plusClearance.toPx() } } }
    Box(contentAlignment = Alignment.TopCenter) {
        Icon(
            modifier = Modifier
                .offset(y = -plusSize / 2)
                .clip(CircleShape)
                .clickable { onPlusClick() }
                .background(color = colorScheme.primary, shape = CircleShape)
                .padding(plusPadding)
                .size(plusIconSize),
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = colorScheme.onPrimary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithCache {
                    val path = Path()
                    path.moveTo(0f, 0f)
                    path.lineTo((size.width - plusSizePx) / 2f - plusClearancePx, 0f)
                    path.arcTo(
                        rect = Rect(
                            center = Offset(size.width / 2, 0f),
                            radius = plusSizePx / 2f + plusClearancePx
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false,
                    )
                    path.lineTo(size.width, 0f)
                    path.lineTo(size.width, size.height)
                    path.lineTo(0f, size.height)
                    path.close()

                    onDrawBehind {
                        drawPath(path, color = colorScheme.background)
                    }
                }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomBarScreen.entries.forEachIndexed { index, bottomBarScreen ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onScreenClick(bottomBarScreen) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentScreen == bottomBarScreen) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = bottomBarScreen.selectedIcon,
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = bottomBarScreen.icon,
                            contentDescription = null,
                            tint = colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = bottomBarScreen.text),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = with(colorScheme) { if (currentScreen == bottomBarScreen) primary else secondary }
                    )
                }
                if (index == (BottomBarScreen.entries.size / 2) - 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
private fun BottomAppBarPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        BottomAppBar(currentScreen = BottomBarScreen.HOME)
    }
}