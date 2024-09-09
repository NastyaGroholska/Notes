package com.ahrokholska.notes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "interesting_idea_note",
    indices = [Index(value = ["is_finished"]), Index(value = ["is_pinned"])]
)
data class InterestingIdeaNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val body: String,
    @ColumnInfo(name = "is_finished") val isFinished: Boolean,
    @ColumnInfo(name = "is_pinned") val isPinned: Boolean
)