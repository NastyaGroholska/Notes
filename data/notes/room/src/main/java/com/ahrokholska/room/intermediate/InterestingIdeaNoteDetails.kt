package com.ahrokholska.room.intermediate

import androidx.room.Embedded
import com.ahrokholska.room.entities.InterestingIdeaNoteEntity

internal data class InterestingIdeaNoteDetails(
    @Embedded
    val note: InterestingIdeaNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)