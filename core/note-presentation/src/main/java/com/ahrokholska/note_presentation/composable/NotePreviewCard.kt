package com.ahrokholska.note_presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ahrokholska.note_presentation.composable.notes.BuySomethingNote
import com.ahrokholska.note_presentation.composable.notes.GoalsNote
import com.ahrokholska.note_presentation.composable.notes.GuidanceNote
import com.ahrokholska.note_presentation.composable.notes.InterestingIdeaNote
import com.ahrokholska.note_presentation.composable.notes.RoutineTasksNote
import com.ahrokholska.note_presentation.model.NotePreview
import com.ahrokholska.note_presentation.model.NoteType

@Composable
fun NotePreviewCard(
    modifier: Modifier = Modifier,
    note: NotePreview,
    onNoteClick: (Int, NoteType) -> Unit,
    shouldShowNoteType: Boolean = true
) {
    when (note) {
        is NotePreview.BuyingSomething -> BuySomethingNote(
            modifier = modifier,
            title = note.title,
            items = note.items,
            color = note.color,
            onNoteClick = { onNoteClick(note.id, NoteType.BuyingSomething) },
            shouldShowNoteType = shouldShowNoteType
        )

        is NotePreview.Goals -> GoalsNote(
            modifier = modifier,
            title = note.title,
            tasks = note.tasks,
            color = note.color,
            onNoteClick = { onNoteClick(note.id, NoteType.Goals) },
            shouldShowNoteType = shouldShowNoteType
        )

        is NotePreview.Guidance -> GuidanceNote(
            modifier = modifier,
            title = note.title,
            body = note.body,
            image = note.image,
            color = note.color,
            onNoteClick = { onNoteClick(note.id, NoteType.Guidance) },
            shouldShowNoteType = shouldShowNoteType
        )

        is NotePreview.InterestingIdea -> InterestingIdeaNote(
            modifier = modifier,
            title = note.title,
            text = note.body,
            color = note.color,
            onNoteClick = { onNoteClick(note.id, NoteType.InterestingIdea) },
            shouldShowNoteType = shouldShowNoteType
        )

        is NotePreview.RoutineTasks -> RoutineTasksNote(
            modifier = modifier,
            active = note.active,
            completed = note.completed,
            color = note.color,
            onNoteClick = { onNoteClick(note.id, NoteType.RoutineTasks) },
            shouldShowNoteType = shouldShowNoteType
        )
    }
}