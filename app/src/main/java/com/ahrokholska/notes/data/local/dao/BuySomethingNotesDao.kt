package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteEntity
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteEntityWithItems
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteItem
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BuySomethingNotesDao {
    @Transaction
    open fun delete(
        noteId: Int, unpinNote: () -> Unit,
        deleteFinishedRecord: () -> Unit
    ) {
        deleteBuySomethingNoteOnly(noteId)
        unpinNote()
        deleteFinishedRecord()
    }

    @Query("DELETE FROM buy_something_note WHERE id = :noteId")
    protected abstract fun deleteBuySomethingNoteOnly(noteId: Int)

    @Insert
    protected abstract fun insertBuySomethingNoteEntity(note: BuySomethingNoteEntity): Long

    @Insert
    protected abstract fun insertBuySomethingNoteItem(item: BuySomethingNoteItem)

    @Query("SELECT * FROM buy_something_note WHERE rowid=:rowId")
    protected abstract fun getBuySomethingNoteByRowId(rowId: Long): BuySomethingNoteEntity

    @Transaction
    open fun insert(note: Note.BuyingSomething) {
        val rowId = insertBuySomethingNoteEntity(BuySomethingNoteEntity(title = note.title))
        val buyNote = getBuySomethingNoteByRowId(rowId)
        note.items.forEachIndexed { index, item ->
            insertBuySomethingNoteItem(
                BuySomethingNoteItem(
                    noteId = buyNote.id,
                    itemIndex = index,
                    checked = item.first,
                    text = item.second
                )
            )
        }
    }

    @Query(
        "SELECT buy_something_note.*" +
                "FROM buy_something_note " +
                "LEFT JOIN  finished_notes " +
                "ON buy_something_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null"
    )
    protected abstract fun getAllBuySomethingNotesGen(type: NoteType = NoteType.BuyingSomething): Flow<List<BuySomethingNoteEntityWithItems>>

    fun getAllBuySomethingNotes() = getAllBuySomethingNotesGen()

    @Query(
        "SELECT buy_something_note.*" +
                "FROM buy_something_note " +
                "LEFT JOIN  finished_notes " +
                "ON buy_something_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null " +
                "ORDER BY id DESC LIMIT 10"
    )
    protected abstract fun getLast10BuySomethingNotesGen(type: NoteType = NoteType.BuyingSomething): Flow<List<BuySomethingNoteEntityWithItems>>

    fun getLast10BuySomethingNotes() = getLast10BuySomethingNotesGen()
}