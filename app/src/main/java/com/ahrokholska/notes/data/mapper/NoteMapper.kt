package com.ahrokholska.notes.data.mapper

import com.ahrokholska.notes.data.local.entities.BuySomethingNoteEntityWithItems
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NotePreview

fun Note.toEntity() = when (this) {
    is Note.BuyingSomething -> TODO()
    is Note.Goals -> TODO()
    is Note.Guidance -> TODO()
    is Note.InterestingIdea -> toEntity()
    is Note.RoutineTasks -> TODO()
}

fun Note.InterestingIdea.toEntity() = InterestingIdeaNoteEntity(
    id = id,
    title = title,
    body = body
)

fun InterestingIdeaNoteEntity.toDomain(isFinished: Boolean, isPinned: Boolean) =
    Note.InterestingIdea(
        id = id,
        title = title,
        body = body,
        isFinished = isFinished,
        isPinned = isPinned
    )

fun InterestingIdeaNoteEntity.toDomainPreview() =
    NotePreview.InterestingIdea(
        id = id,
        title = title,
        body = body
    )

fun BuySomethingNoteEntityWithItems.toDomainPreview() = NotePreview.BuyingSomething(
    id = note.id,
    title = note.title,
    items = items.map { it.checked to it.text }
)