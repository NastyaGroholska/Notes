package com.ahrokholska.notes.domain.repository

import com.ahrokholska.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface NotesRepository {
    suspend fun saveNote(note: Note): Result<Unit>
    suspend fun deleteNote(noteId: Int, noteType: KClass<Note>): Result<Unit>
    fun getAllInterestingIdeaNotes(): Flow<List<Note.InterestingIdea>>
    fun getLast10InterestingIdeaNotes(): Flow<List<Note.InterestingIdea>>
}