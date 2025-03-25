package com.ahrokholska.api.model

data class NoteTitle(
    val title: String,
    val id: Int,
    val type: NoteType
)