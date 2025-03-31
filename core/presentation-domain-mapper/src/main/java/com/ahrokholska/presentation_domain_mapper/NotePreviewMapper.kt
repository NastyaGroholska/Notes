package com.ahrokholska.presentation_domain_mapper

import com.ahrokholska.note_presentation.model.NotePreview
import com.ahrokholska.note_presentation.theme.noteColors

fun com.ahrokholska.api.model.NotePreview.toUI(index: Int) = when (this) {
    is com.ahrokholska.api.model.NotePreview.BuyingSomething -> toUI(index, true)
    is com.ahrokholska.api.model.NotePreview.Goals -> toUI(index, true)
    is com.ahrokholska.api.model.NotePreview.Guidance -> toUI(index, true)
    is com.ahrokholska.api.model.NotePreview.InterestingIdea -> toUI(index, true)
    is com.ahrokholska.api.model.NotePreview.RoutineTasks -> toUI(index, true)
}

fun com.ahrokholska.api.model.NotePreview.BuyingSomething.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.BuyingSomething(
    id = id,
    title = title,
    items = items,
    color = noteColors[(if (inCombinedList) index else index + 2) % noteColors.size]
)

fun com.ahrokholska.api.model.NotePreview.Goals.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.Goals(
    id = id,
    title = title,
    tasks = tasks.toTaskPresentation(),
    color = noteColors[(if (inCombinedList) index else index + 3) % noteColors.size]
)

fun com.ahrokholska.api.model.NotePreview.Guidance.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.Guidance(
    id = id,
    title = title,
    body = body,
    image = image,
    color = noteColors[(if (inCombinedList) index else index + 4) % noteColors.size]
)

fun com.ahrokholska.api.model.NotePreview.InterestingIdea.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.InterestingIdea(
    id = id,
    title = title,
    body = body,
    color = noteColors[(if (inCombinedList) index else index + 1) % noteColors.size]
)

fun com.ahrokholska.api.model.NotePreview.RoutineTasks.toUI(
    index: Int, inCombinedList: Boolean = false
) = NotePreview.RoutineTasks(
    id = id,
    active = active.toPresentation(),
    completed = completed.toPresentation(),
    color = noteColors[(if (inCombinedList) index else index + 5) % noteColors.size]
)