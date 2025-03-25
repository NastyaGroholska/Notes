package com.ahrokholska.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "goals_note_subtask",
    primaryKeys = ["note_id", "task_index", "subtask_index"],
    foreignKeys = [ForeignKey(
        entity = GoalsNoteTaskEntity::class,
        parentColumns = ["note_id", "task_index"],
        childColumns = ["note_id", "task_index"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class GoalsNoteSubtaskEntity(
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "task_index") val taskIndex: Int,
    @ColumnInfo(name = "subtask_index") val subtaskIndex: Int,
    val checked: Boolean,
    val text: String
)