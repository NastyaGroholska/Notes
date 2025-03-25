package com.ahrokholska.room.intermediate

import androidx.room.Embedded

internal data class RoutineTasksNoteDetails(
    @Embedded
    val note: RoutineTasksNoteEntityWithSubNotes,
    val isFinished: Boolean,
    val isPinned: Boolean
)