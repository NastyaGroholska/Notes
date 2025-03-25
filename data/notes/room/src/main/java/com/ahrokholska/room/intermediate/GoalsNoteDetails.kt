package com.ahrokholska.room.intermediate

import androidx.room.Embedded
import com.ahrokholska.room.entities.GoalsNoteEntity

internal data class GoalsNoteDetails(
    @Embedded
    val note: GoalsNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)