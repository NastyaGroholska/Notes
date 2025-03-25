package com.ahrokholska.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interesting_idea_note")
internal data class InterestingIdeaNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val body: String,
)