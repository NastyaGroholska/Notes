package com.ahrokholska.notes.data.mapper

import com.ahrokholska.notes.domain.model.NoteTitle
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<com.ahrokholska.notes.data.local.intermediate.NoteTitle>>.toDomain(type: NoteType) =
    map { list -> list.map { NoteTitle(it.title, it.id, type) } }