package com.ahrokholska.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.ahrokholska.api.model.NoteType

@Entity(
    tableName = "pinned_notes",
    primaryKeys = ["note_id", "note_type"],
    indices = [Index(value = ["note_type"])]
)
internal data class PinnedNoteEntity(
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "note_type") val noteType: NoteType,
    @ColumnInfo(defaultValue = "0") val time: Long
)