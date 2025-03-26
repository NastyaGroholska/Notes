package com.ahrokholska.notes.presentation.screens.createNewNotes.fill

import androidx.compose.runtime.Composable
import com.ahrokholska.note_presentation.model.NoteType
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.buy.BuySomethingNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.goal.GoalsNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.guidance.GuidanceNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.idea.InterestingIdeaNoteScreen
import com.ahrokholska.notes.presentation.screens.createNewNotes.fill.screenTypes.routine.RoutineTasksNoteScreen

@Composable
fun CreateNoteScreen(type: NoteType, onBackClick: () -> Unit, onNoteSaved: () -> Unit) {
    when (type) {
        NoteType.InterestingIdea -> InterestingIdeaNoteScreen(
            onBackClick = onBackClick,
            onNoteSaved = onNoteSaved
        )

        NoteType.BuyingSomething -> BuySomethingNoteScreen(
            onBackClick = onBackClick,
            onNoteSaved = onNoteSaved
        )

        NoteType.Goals -> GoalsNoteScreen(onBackClick = onBackClick, onNoteSaved = onNoteSaved)
        NoteType.Guidance -> GuidanceNoteScreen(
            onBackClick = onBackClick,
            onNoteSaved = onNoteSaved
        )

        NoteType.RoutineTasks -> RoutineTasksNoteScreen(
            onBackClick = onBackClick,
            onNoteSaved = onNoteSaved
        )
    }
}