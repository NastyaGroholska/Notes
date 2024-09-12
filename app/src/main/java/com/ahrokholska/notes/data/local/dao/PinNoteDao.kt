package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ahrokholska.notes.domain.model.NoteType

@Dao
interface PinNoteDao {
    @Query("DELETE FROM pinned_notes WHERE note_id = :noteId AND note_type = :type")
    fun unpinNote(noteId: Int, type: NoteType)
}