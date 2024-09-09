package com.ahrokholska.notes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert
    fun insertInterestingIdeaNote(note: InterestingIdeaNoteEntity)

    @Query("DELETE FROM interesting_idea_note WHERE id = :noteId")
    fun deleteInterestingIdeaNote(noteId: Int)

    @Query("SELECT * FROM interesting_idea_note")
    fun getAllInterestingIdeaNotes(): Flow<List<InterestingIdeaNoteEntity>>

    @Query("SELECT * FROM interesting_idea_note ORDER BY id LIMIT 10")
    fun getLast10InterestingIdeaNotes(): Flow<List<InterestingIdeaNoteEntity>>
}