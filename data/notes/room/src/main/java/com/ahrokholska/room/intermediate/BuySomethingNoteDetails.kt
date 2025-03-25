package com.ahrokholska.room.intermediate

import androidx.room.Embedded

internal data class BuySomethingNoteDetails(
    @Embedded
    val noteWithItems: BuySomethingNoteEntityWithItems,
    val isFinished: Boolean,
    val isPinned: Boolean
)