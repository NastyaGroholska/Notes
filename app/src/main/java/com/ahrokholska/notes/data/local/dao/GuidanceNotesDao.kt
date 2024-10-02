package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.entities.GuidanceNoteEntity
import com.ahrokholska.notes.data.local.intermediate.GuidanceNoteDetails
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GuidanceNotesDao {
    @Insert
    abstract fun insert(note: GuidanceNoteEntity)

    @Transaction
    open fun delete(
        noteId: Int,
        unpinNote: () -> Unit,
        deleteFinishedRecord: () -> Unit
    ) {
        deleteGuidanceNoteOnly(noteId)
        unpinNote()
        deleteFinishedRecord()
    }

    @Query("DELETE FROM guidance_note WHERE id = :noteId")
    protected abstract fun deleteGuidanceNoteOnly(noteId: Int)

    @Query(
        "SELECT guidance_note.*" +
                "FROM guidance_note " +
                "LEFT JOIN  finished_notes " +
                "ON guidance_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null"
    )
    protected abstract fun getAllGuidanceNotesGen(type: NoteType = NoteType.Guidance): Flow<List<GuidanceNoteEntity>>

    fun getAllGuidanceNotes() = getAllGuidanceNotesGen()

    @Query(
        "SELECT guidance_note.*" +
                "FROM guidance_note " +
                "LEFT JOIN  finished_notes " +
                "ON guidance_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null " +
                "ORDER BY id DESC LIMIT 10"
    )
    protected abstract fun getLast10GuidanceNotesGen(type: NoteType = NoteType.Guidance): Flow<List<GuidanceNoteEntity>>

    fun getLast10GuidanceNotes() = getLast10GuidanceNotesGen()


    @Query(
        "SELECT guidance_note.*, NOT finished_notes.note_id is NULL as isFinished,NOT pinned_notes.note_id is NULL as isPinned " +
                "FROM (SELECT * FROM guidance_note WHERE guidance_note.id = :id) guidance_note " +
                "LEFT JOIN finished_notes ON finished_notes.note_id = :id AND finished_notes.note_type = :type " +
                "LEFT JOIN pinned_notes ON pinned_notes.note_id = :id AND pinned_notes.note_type = :type"
    )
    protected abstract fun getGuidanceNoteDetailsGen(
        id: Int, type: NoteType = NoteType.Guidance
    ): Flow<GuidanceNoteDetails>

    fun getGuidanceNoteDetails(id: Int) = getGuidanceNoteDetailsGen(id)
}