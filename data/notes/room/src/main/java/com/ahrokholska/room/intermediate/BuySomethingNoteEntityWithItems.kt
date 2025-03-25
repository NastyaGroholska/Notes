package com.ahrokholska.room.intermediate

import androidx.room.Embedded
import androidx.room.Relation
import com.ahrokholska.room.entities.BuySomethingNoteEntity
import com.ahrokholska.room.entities.BuySomethingNoteItemEntity

internal data class BuySomethingNoteEntityWithItems(
    @Embedded
    val note: BuySomethingNoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val items: List<BuySomethingNoteItemEntity>
)