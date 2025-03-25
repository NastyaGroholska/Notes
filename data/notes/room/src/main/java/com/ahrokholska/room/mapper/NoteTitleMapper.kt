package com.ahrokholska.room.mapper

import com.ahrokholska.api.model.NoteTitle
import com.ahrokholska.api.model.NoteType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun Flow<List<com.ahrokholska.room.intermediate.NoteTitle>>.toDomain(type: NoteType) =
    map { list -> list.map { NoteTitle(it.title, it.id, type) } }