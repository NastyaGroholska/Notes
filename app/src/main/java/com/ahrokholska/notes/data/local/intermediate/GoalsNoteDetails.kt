package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded
import com.ahrokholska.notes.data.local.entities.GoalsNoteEntity

data class GoalsNoteDetails(
    @Embedded
    val note: GoalsNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)