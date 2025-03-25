package com.ahrokholska.room.intermediate

import androidx.room.Embedded
import com.ahrokholska.room.entities.GuidanceNoteEntity

internal data class GuidanceNoteDetails(
    @Embedded
    val note: GuidanceNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)