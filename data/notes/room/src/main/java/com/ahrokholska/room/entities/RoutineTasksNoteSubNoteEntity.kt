package com.ahrokholska.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "routine_tasks_note_sub_note",
    primaryKeys = ["note_id", "item_index"],
    foreignKeys = [ForeignKey(
        entity = RoutineTasksNoteEntity::class,
        parentColumns = ["id"],
        childColumns = ["note_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class RoutineTasksNoteSubNoteEntity(
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "item_index") val itemIndex: Int,
    val completed: Boolean,
    val title: String,
    val text: String
)