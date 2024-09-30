package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.intermediate.InterestingIdeaNoteDetails
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class InterestingIdeaNotesDao {
    @Insert
    abstract fun insert(note: InterestingIdeaNoteEntity)

    @Transaction
    open fun delete(
        noteId: Int,
        unpinNote: () -> Unit,
        deleteFinishedRecord: () -> Unit
    ) {
        deleteInterestingIdeaNoteOnly(noteId)
        unpinNote()
        deleteFinishedRecord()
    }

    @Query("DELETE FROM interesting_idea_note WHERE id = :noteId")
    protected abstract fun deleteInterestingIdeaNoteOnly(noteId: Int)

    @Query(
        "SELECT interesting_idea_note.*" +
                "FROM interesting_idea_note " +
                "LEFT JOIN  finished_notes " +
                "ON interesting_idea_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null"
    )
    protected abstract fun getAllInterestingIdeaNotesGen(type: NoteType = NoteType.InterestingIdea): Flow<List<InterestingIdeaNoteEntity>>

    fun getAllInterestingIdeaNotes() = getAllInterestingIdeaNotesGen()

    @Query(
        "SELECT interesting_idea_note.*" +
                "FROM interesting_idea_note " +
                "LEFT JOIN  finished_notes " +
                "ON interesting_idea_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null " +
                "ORDER BY id DESC LIMIT 10"
    )
    protected abstract fun getLast10InterestingIdeaNotesGen(type: NoteType = NoteType.InterestingIdea): Flow<List<InterestingIdeaNoteEntity>>

    fun getLast10InterestingIdeaNotes() = getLast10InterestingIdeaNotesGen()

    @Query(
        "SELECT interesting_note.*, NOT finished_notes.note_id is NULL as isFinished,NOT pinned_notes.note_id is NULL as isPinned " +
                "FROM (SELECT interesting_idea_note.* FROM interesting_idea_note WHERE interesting_idea_note.id = :id) interesting_note " +
                "LEFT JOIN finished_notes ON finished_notes.note_id = :id AND finished_notes.note_type = :type " +
                "LEFT JOIN pinned_notes ON pinned_notes.note_id = :id AND pinned_notes.note_type = :type"
    )
    protected abstract fun getInterestingIdeaNoteDetailsGen(
        id: Int, type: NoteType = NoteType.InterestingIdea
    ): Flow<InterestingIdeaNoteDetails>

    fun getInterestingIdeaNoteDetails(id: Int) = getInterestingIdeaNoteDetailsGen(id)
}