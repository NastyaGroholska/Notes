package com.ahrokholska.notes.data.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {
    @Insert
    abstract fun insertInterestingIdeaNote(note: InterestingIdeaNoteEntity)

    @Transaction
    open fun deleteInterestingIdeaNote(noteId: Int) {
        deleteInterestingIdeaNoteOnly(noteId)
        unpinNote(noteId, NoteType.InterestingIdea)
        deleteFinishedRecord(noteId, NoteType.InterestingIdea)
    }

    @Query("DELETE FROM interesting_idea_note WHERE id = :noteId")
    protected abstract fun deleteInterestingIdeaNoteOnly(noteId: Int)

    @Query("DELETE FROM pinned_notes WHERE note_id = :noteId AND note_type = :type")
    abstract fun unpinNote(noteId: Int, type: NoteType)

    @Query("DELETE FROM finished_notes WHERE note_id = :noteId AND note_type = :type")
    protected abstract fun deleteFinishedRecord(noteId: Int, type: NoteType)

    @Query(
        "SELECT interesting_idea_note.*" +
                "FROM interesting_idea_note " +
                "LEFT JOIN (SELECT note_id as finished_note_id FROM finished_notes WHERE finished_notes.note_type = :type) " +
                "ON interesting_idea_note.id = finished_note_id " +
                "WHERE finished_note_id is null"
    )
    abstract fun getAllInterestingIdeaNotes(type: NoteType = NoteType.InterestingIdea): Flow<List<InterestingIdeaNoteEntity>>

    @Query(
        "SELECT interesting_idea_note.*" +
                "FROM interesting_idea_note " +
                "LEFT JOIN (SELECT note_id as finished_note_id FROM finished_notes WHERE finished_notes.note_type = :type) " +
                "ON interesting_idea_note.id = finished_note_id " +
                "WHERE finished_note_id is null " +
                "ORDER BY id LIMIT 10"
    )
    abstract fun getLast10InterestingIdeaNotes(type: NoteType = NoteType.InterestingIdea): Flow<List<InterestingIdeaNoteEntity>>
}