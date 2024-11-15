package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahrokholska.notes.data.local.entities.PinnedNoteEntity
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
interface PinNoteDao {
    @Query("DELETE FROM pinned_notes WHERE note_id = :noteId AND note_type = :type")
    fun unpinNote(noteId: Int, type: NoteType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun pinNote(note: PinnedNoteEntity)

    @Query("SELECT * FROM pinned_notes ORDER BY time DESC LIMIT 10")

    fun getLast10PinnedNotes(): Flow<List<PinnedNoteEntity>>

    @Query("SELECT * FROM pinned_notes ORDER BY time")
    fun getAllPinnedNotes(): Flow<List<PinnedNoteEntity>>
}