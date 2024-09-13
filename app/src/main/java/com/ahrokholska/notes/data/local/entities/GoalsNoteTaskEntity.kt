package com.ahrokholska.notes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "goals_note_task",
    primaryKeys = ["note_id", "task_index"],
    foreignKeys = [ForeignKey(
        entity = GoalsNoteEntity::class,
        parentColumns = ["id"],
        childColumns = ["note_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GoalsNoteTaskEntity(
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "task_index") val taskIndex: Int,
    val checked: Boolean,
    val text: String
)