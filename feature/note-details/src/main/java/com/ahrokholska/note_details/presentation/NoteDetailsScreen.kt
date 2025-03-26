package com.ahrokholska.note_details.presentation

import androidx.compose.runtime.Composable
import com.ahrokholska.note_details.presentation.noteTypes.buy.BuyingSomethingDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.goal.GoalsDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.guidance.GuidanceDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.idea.InterestingIdeaDetailsScreen
import com.ahrokholska.note_details.presentation.noteTypes.routine.RoutineTasksDetailsScreen
import com.ahrokholska.note_presentation.model.NoteType

@Composable
internal fun NoteDetailsScreen(type: NoteType, onBackClick: () -> Unit = {}) {
    when (type) {
        NoteType.InterestingIdea -> InterestingIdeaDetailsScreen(onBackClick = onBackClick)
        NoteType.BuyingSomething -> BuyingSomethingDetailsScreen(
            onBackClick = onBackClick
        )
        NoteType.Goals -> GoalsDetailsScreen(onBackClick = onBackClick)
        NoteType.Guidance -> GuidanceDetailsScreen(onBackClick = onBackClick)
        NoteType.RoutineTasks -> RoutineTasksDetailsScreen(onBackClick = onBackClick)
    }
}