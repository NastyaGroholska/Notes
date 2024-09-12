package com.ahrokholska.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buy_something_note")
data class BuySomethingNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
)