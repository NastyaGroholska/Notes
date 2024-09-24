package com.ahrokholska.notes.presentation.screens.noteDetails

import androidx.compose.runtime.Composable
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.screens.noteDetails.buy.BuyingSomethingDetailsScreen
import com.ahrokholska.notes.presentation.screens.noteDetails.idea.InterestingIdeaDetailsScreen

@Composable
fun NoteDetailsScreen(type: NoteType, onBackClick: () -> Unit = {}) {
    when (type) {
        NoteType.InterestingIdea -> InterestingIdeaDetailsScreen(onBackClick = onBackClick)
        NoteType.BuyingSomething -> BuyingSomethingDetailsScreen(onBackClick = onBackClick)
        NoteType.Goals -> TODO()
        NoteType.Guidance -> TODO()
        NoteType.RoutineTasks -> TODO()
    }
}