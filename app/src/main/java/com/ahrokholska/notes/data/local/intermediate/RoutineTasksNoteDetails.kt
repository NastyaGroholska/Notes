package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded

data class RoutineTasksNoteDetails(
    @Embedded
    val note: RoutineTasksNoteEntityWithSubNotes,
    val isFinished: Boolean,
    val isPinned: Boolean
)