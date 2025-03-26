package com.ahrokholska.presentation_domain_mapper

import com.ahrokholska.api.model.Note
import com.ahrokholska.note_presentation.theme.noteColors

fun Note.toPresentation() = when (this) {
    is Note.BuyingSomething -> toPresentation()
    is Note.Goals -> toPresentation()
    is Note.Guidance -> toPresentation()
    is Note.InterestingIdea -> toPresentation()
    is Note.RoutineTasks -> toPresentation()
}

fun Note.BuyingSomething.toPresentation() =
    com.ahrokholska.note_presentation.model.Note.BuyingSomething(
        id = id,
        title = title,
        items = items,
        isFinished = isFinished,
        isPinned = isPinned,
        color = noteColors[0]
    )

fun Note.Goals.toPresentation() = com.ahrokholska.note_presentation.model.Note.Goals(
    id = id,
    title = title,
    tasks = tasks.toTaskPresentation(),
    isFinished = isFinished,
    isPinned = isPinned,
    color = noteColors[0]
)

fun List<Pair<Note.Goals.Task, List<Note.Goals.Task>>>.toTaskPresentation() = map { pair ->
    pair.first.toPresentation() to pair.second.toListPresentation()
}

fun List<Note.Goals.Task>.toListPresentation() = map { it.toPresentation() }

fun Note.Goals.Task.toPresentation() = com.ahrokholska.note_presentation.model.Note.Goals.Task(
    finished = finished,
    text = text
)

fun Note.Guidance.toPresentation() = com.ahrokholska.note_presentation.model.Note.Guidance(
    id = id,
    title = title,
    body = body,
    image = image,
    isFinished = isFinished,
    isPinned = isPinned,
    color = noteColors[0]
)

fun Note.InterestingIdea.toPresentation() =
    com.ahrokholska.note_presentation.model.Note.InterestingIdea(
        id = id,
        title = title,
        body = body,
        isFinished = isFinished,
        isPinned = isPinned,
        color = noteColors[0]
    )

fun Note.RoutineTasks.toPresentation() = com.ahrokholska.note_presentation.model.Note.RoutineTasks(
    id = id,
    active = active.toPresentation(),
    completed = completed.toPresentation(),
    isFinished = isFinished,
    isPinned = isPinned,
    color = noteColors[0]
)

fun List<Note.RoutineTasks.SubNote>.toPresentation() = map { it.toSubNotePresentation() }

fun Note.RoutineTasks.SubNote.toSubNotePresentation() =
    com.ahrokholska.note_presentation.model.Note.RoutineTasks.SubNote(
        id = id,
        title = title,
        text = text
    )