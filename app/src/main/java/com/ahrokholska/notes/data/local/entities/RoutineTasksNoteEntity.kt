package com.ahrokholska.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_tasks_note")
data class RoutineTasksNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)