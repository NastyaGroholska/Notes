package com.ahrokholska.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buy_something_note")
internal data class BuySomethingNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
)