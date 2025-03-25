package com.ahrokholska.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.api.model.NoteType
import com.ahrokholska.room.entities.FinishedNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FinishNoteDao {
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