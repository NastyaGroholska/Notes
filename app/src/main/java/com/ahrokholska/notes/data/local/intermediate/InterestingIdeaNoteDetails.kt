package com.ahrokholska.notes.data.local.intermediate

import androidx.room.Embedded
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity

data class InterestingIdeaNoteDetails(
    @Embedded
    val note: InterestingIdeaNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)