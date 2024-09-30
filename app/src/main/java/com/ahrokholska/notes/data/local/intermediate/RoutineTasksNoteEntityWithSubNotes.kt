package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded
import androidx.room.Relation
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteEntity
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteSubNoteEntity

data class RoutineTasksNoteEntityWithSubNotes(
    @Embedded
    val note: RoutineTasksNoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val subNotes: List<RoutineTasksNoteSubNoteEntity>
)