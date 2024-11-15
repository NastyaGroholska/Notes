package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.entities.FinishedNoteEntity
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
interface FinishNoteDao {
    @Query("DELETE FROM finished_notes WHERE note_id = :noteId AND note_type = :type")
    fun deleteFinishedRecord(noteId: Int, type: NoteType)

    @Insert
    fun insertFinishRecord(record: FinishedNoteEntity)

    @Transaction
    fun finishNote(noteId: Int, type: NoteType, time: Long, unpinNote: () -> Unit) {
        unpinNote()
        insertFinishRecord(FinishedNoteEntity(noteId, type, time))
    }

    @Query("SELECT * FROM finished_notes ORDER BY time")
    fun getAllFinishedNotes(): Flow<List<FinishedNoteEntity>>
}