package com.ahrokholska.notes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.ahrokholska.notes.domain.model.NoteType

@Entity(
    tableName = "finished_notes",
    primaryKeys = ["note_id", "note_type"],
    indices = [Index(value = ["note_type"])]
)
data class FinishedNoteEntity(
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "note_type") val noteType: NoteType,
    @ColumnInfo(defaultValue = "0") val time: Long
)