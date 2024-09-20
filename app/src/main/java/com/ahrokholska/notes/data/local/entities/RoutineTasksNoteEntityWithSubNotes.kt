package com.ahrokholska.notes.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineTasksNoteEntityWithSubNotes(
    @Embedded
    val note: RoutineTasksNoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val subNotes: List<RoutineTasksNoteSubNoteEntity>
)