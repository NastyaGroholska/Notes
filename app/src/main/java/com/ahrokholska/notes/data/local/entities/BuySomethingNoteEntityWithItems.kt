package com.ahrokholska.notes.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class BuySomethingNoteEntityWithItems(
    @Embedded
    val note: BuySomethingNoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val items: List<BuySomethingNoteItem>
)