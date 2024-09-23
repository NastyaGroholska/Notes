package com.ahrokholska.notes.data.local.entities

import androidx.room.Embedded

data class InterestingIdeaNoteDetails(
    @Embedded
    val note: InterestingIdeaNoteEntity,
    val isFinished: Boolean,
    val isPinned: Boolean
)