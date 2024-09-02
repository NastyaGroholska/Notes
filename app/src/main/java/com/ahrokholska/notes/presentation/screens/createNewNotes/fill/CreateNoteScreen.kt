package com.ahrokholska.notes.presentation.screens.createNewNotes.fill

import androidx.compose.runtime.Composable
import com.ahrokholska.notes.presentation.model.NoteType
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.buy.BuySomethingNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.idea.InterestingIdeaNoteScreen

@Composable
fun CreateNoteScreen(type: NoteType, onBackClick: () -> Unit) {
    when (type) {
        NoteType.InterestingIdea -> InterestingIdeaNoteScreen(onBackClick = onBackClick)
        NoteType.BuyingSomething -> BuySomethingNoteScreen(onBackClick = onBackClick)
        NoteType.Goals -> TODO()
        NoteType.Guidance -> TODO()
        NoteType.RoutineTasks -> TODO()
    }
}