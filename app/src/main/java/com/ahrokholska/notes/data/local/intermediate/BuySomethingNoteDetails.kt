package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded

data class BuySomethingNoteDetails(
    @Embedded
    val noteWithItems: BuySomethingNoteEntityWithItems,
    val isFinished: Boolean,
    val isPinned: Boolean
)