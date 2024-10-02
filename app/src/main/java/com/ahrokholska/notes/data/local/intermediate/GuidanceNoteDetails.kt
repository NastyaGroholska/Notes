package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded
import com.ahrokholska.notes.data.local.entities.GuidanceNoteEntity

data class GuidanceNoteDetails(
    @Embedded
    val note: GuidanceNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)