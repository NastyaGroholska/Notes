package com.ahrokholska.notes.domain.repository

import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NotePreview
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface NotesRepository {
    suspend fun saveNote(note: Note): Result<Unit>
    suspend fun deleteNote(noteId: Int, noteType: KClass<Note>): Result<Unit>
    fun getAllInterestingIdeaNotes(): Flow<List<NotePreview.InterestingIdea>>
    fun getLast10InterestingIdeaNotes(): Flow<List<NotePreview.InterestingIdea>>
    fun getAllBuySomethingNotes(): Flow<List<NotePreview.BuyingSomething>>
    fun getLast10BuySomethingNotes(): Flow<List<NotePreview.BuyingSomething>>
}