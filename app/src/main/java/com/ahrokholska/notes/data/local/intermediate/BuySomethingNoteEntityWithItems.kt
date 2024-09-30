package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded
import androidx.room.Relation
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteEntity
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteItemEntity

data class BuySomethingNoteEntityWithItems(
    @Embedded
    val note: BuySomethingNoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val items: List<BuySomethingNoteItemEntity>
)