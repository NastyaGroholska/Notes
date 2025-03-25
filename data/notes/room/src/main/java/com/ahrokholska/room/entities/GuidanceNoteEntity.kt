package com.ahrokholska.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guidance_note")
internal data class GuidanceNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val body: String,
    val image: String
)