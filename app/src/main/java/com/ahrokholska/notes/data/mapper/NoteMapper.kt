package com.ahrokholska.notes.data.mapper

import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.domain.model.Note

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
    body = body,
    isFinished = isFinished,
    isPinned = isPinned
)

fun InterestingIdeaNoteEntity.toDomain() = Note.InterestingIdea(
    id = id,
    title = title,
    body = body,
    isFinished = isFinished,
    isPinned = isPinned
)