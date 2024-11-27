package com.ahrokholska.notes.domain.model

data class NoteTitle(
    val title: String,
    val id: Int,
    val type: NoteType
)