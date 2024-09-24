package com.ahrokholska.notes.data.local.entities

import androidx.room.Embedded

data class BuySomethingNoteDetails(
    @Embedded
    val noteWithItems: BuySomethingNoteEntityWithItems,
    val isFinished: Boolean,
    val isPinned: Boolean
)