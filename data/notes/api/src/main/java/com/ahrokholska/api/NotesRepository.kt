package com.ahrokholska.api

import com.ahrokholska.api.model.Note
import com.ahrokholska.api.model.NotePreview
import com.ahrokholska.api.model.NoteTitle
import com.ahrokholska.api.model.NoteType
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun saveNote(note: Note): Result<Unit>
    suspend fun deleteNote(noteId: Int, noteType: NoteType): Result<Unit>
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
    fun getInterestingIdeaNoteDetails(noteId: Int): Flow<Note.InterestingIdea?>
    fun getBuySomethingNoteDetails(noteId: Int): Flow<Note.BuyingSomething?>
    fun getGoalsNoteDetails(noteId: Int): Flow<Note.Goals?>
    fun getGuidanceNoteDetails(noteId: Int): Flow<Note.Guidance?>
    fun getRoutineTasksNoteDetails(noteId: Int): Flow<Note.RoutineTasks?>
    suspend fun changeBuySomethingItemCheck(noteId: Int, index: Int, checked: Boolean): Result<Unit>
    suspend fun changeGoalsTaskCheck(noteId: Int, index: Int, checked: Boolean): Result<Unit>
    suspend fun changeGoalsSubtaskCheck(
        noteId: Int, taskIndex: Int, subtaskIndex: Int, checked: Boolean
    ): Result<Unit>

    suspend fun changeRoutineTasksSubNoteCheck(
        noteId: Int, index: Int, finished: Boolean
    ): Result<Unit>

    fun getLast10PinnedNotes(): Flow<List<NotePreview>>
    fun getAllPinnedNotes(): Flow<List<NotePreview>>
    suspend fun pinNote(noteId: Int, noteType: NoteType, time: Long)
    suspend fun unpinNote(noteId: Int, noteType: NoteType)
    suspend fun finishNote(noteId: Int, noteType: NoteType, time: Long)
    fun getAllFinishedNotes(): Flow<List<NotePreview>>
    fun getAllTitles(): Flow<List<NoteTitle>>
    suspend fun updateImage(oldImage: String, image: String)
}