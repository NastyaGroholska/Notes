package com.ahrokholska.notes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "buy_something_note_item",
    primaryKeys = ["note_id", "item_index"],
    foreignKeys = [ForeignKey(
        entity = BuySomethingNoteEntity::class,
        parentColumns = ["id"],
        childColumns = ["note_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BuySomethingNoteItem(
    @ColumnInfo(name = "note_id") val noteId: Int,
    @ColumnInfo(name = "item_index") val itemIndex: Int,
    val checked: Boolean,
    val text: String
)