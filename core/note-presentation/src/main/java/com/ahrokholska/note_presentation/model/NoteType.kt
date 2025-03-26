package com.ahrokholska.note_presentation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Task
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.ahrokholska.note_presentation.R

enum class NoteType(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val icon: ImageVector,
    val color: Color
) {
    InterestingIdea(
        R.string.interesting_idea,
        R.string.interesting_idea_descr,
        Icons.Outlined.Lightbulb,
        Color(0xFF6A3EA1)
    ),
    BuyingSomething(
        R.string.buying_something,
        R.string.buying_something_descr,
        Icons.Outlined.ShoppingCart,
        Color(0xFF60D889)
    ),
    Goals(R.string.goals, R.string.goals_descr, Icons.Outlined.Star, Color(0xFFF8C715)),
    Guidance(
        R.string.guidance,
        R.string.guidance_descr,
        Icons.AutoMirrored.Outlined.ListAlt,
        Color(0xFFCE3A54)
    ),
    RoutineTasks(
        R.string.routine_tasks,
        R.string.routine_tasks_descr,
        Icons.Outlined.Task,
        Color(0xFFDEDC52)
    )
}