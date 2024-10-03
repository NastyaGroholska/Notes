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
    fun getAllGoalsNotes(): Flow<List<NotePreview.Goals>>
    fun getLast10GoalsNotes(): Flow<List<NotePreview.Goals>>
    fun getAllGuidanceNotes(): Flow<List<NotePreview.Guidance>>
    fun getLast10GuidanceNotes(): Flow<List<NotePreview.Guidance>>
    fun getAllRoutineTasksNotes(): Flow<List<NotePreview.RoutineTasks>>
    fun getLast10RoutineTasksNotes(): Flow<List<NotePreview.RoutineTasks>>
    fun getInterestingIdeaNoteDetails(noteId: Int): Flow<Note.InterestingIdea>
    fun getBuySomethingNoteDetails(noteId: Int): Flow<Note.BuyingSomething>
    fun getGoalsNoteDetails(noteId: Int): Flow<Note.Goals>
    fun getGuidanceNoteDetails(noteId: Int): Flow<Note.Guidance>
    fun getRoutineTasksNoteDetails(noteId: Int): Flow<Note.RoutineTasks>
    suspend fun changeBuySomethingItemCheck(noteId: Int, index: Int, checked: Boolean): Result<Unit>
    suspend fun changeGoalsTaskCheck(noteId: Int, index: Int, checked: Boolean): Result<Unit>
    suspend fun changeGoalsSubtaskCheck(
        noteId: Int, taskIndex: Int, subtaskIndex: Int, checked: Boolean
    ): Result<Unit>

    suspend fun changeRoutineTasksSubNoteCheck(
        noteId: Int, index: Int, finished: Boolean
    ): Result<Unit>
}