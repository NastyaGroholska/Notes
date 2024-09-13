package com.ahrokholska.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals_note")
data class GoalsNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
)