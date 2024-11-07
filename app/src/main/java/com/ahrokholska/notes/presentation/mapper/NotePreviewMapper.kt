package com.ahrokholska.notes.presentation.mapper

import com.ahrokholska.notes.presentation.model.NotePreview
import com.ahrokholska.notes.presentation.theme.noteColors

fun com.ahrokholska.notes.domain.model.NotePreview.toUI(index: Int) = when (this) {
    is com.ahrokholska.notes.domain.model.NotePreview.BuyingSomething -> toUI(index)
    is com.ahrokholska.notes.domain.model.NotePreview.Goals -> toUI(index)
    is com.ahrokholska.notes.domain.model.NotePreview.Guidance -> toUI(index)
    is com.ahrokholska.notes.domain.model.NotePreview.InterestingIdea -> toUI(index)
    is com.ahrokholska.notes.domain.model.NotePreview.RoutineTasks -> toUI(index)
}

fun com.ahrokholska.notes.domain.model.NotePreview.BuyingSomething.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.BuyingSomething(
    id = id,
    title = title,
    items = items,
    color = noteColors[(if (inCombinedList) index else index + 2) % noteColors.size]
)

fun com.ahrokholska.notes.domain.model.NotePreview.Goals.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.Goals(
    id = id,
    title = title,
    tasks = tasks,
    color = noteColors[(if (inCombinedList) index else index + 3) % noteColors.size]
)

fun com.ahrokholska.notes.domain.model.NotePreview.Guidance.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.Guidance(
    id = id,
    title = title,
    body = body,
    image = image,
    color = noteColors[(if (inCombinedList) index else index + 4) % noteColors.size]
)

fun com.ahrokholska.notes.domain.model.NotePreview.InterestingIdea.toUI(
    index: Int, inCombinedList: Boolean = false
) =
    NotePreview.InterestingIdea(
        id = id,
        title = title,
        body = body,
        color = noteColors[(if (inCombinedList) index else index + 1) % noteColors.size]
    )

fun com.ahrokholska.notes.domain.model.NotePreview.RoutineTasks.toUI(
    index: Int, inCombinedList: Boolean = false
) =
    NotePreview.RoutineTasks(
        id = id,
        active = active,
        completed = completed,
        color = noteColors[(if (inCombinedList) index else index + 5) % noteColors.size]
    )