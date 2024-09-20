package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteEntity
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteEntityWithSubNotes
import com.ahrokholska.notes.data.local.entities.RoutineTasksNoteSubNoteEntity
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RoutineTasksNotesDao {
    @Transaction
    open fun delete(
        noteId: Int, unpinNote: () -> Unit,
        deleteFinishedRecord: () -> Unit
    ) {
        deleteRoutineTasksOnly(noteId)
        unpinNote()
        deleteFinishedRecord()
    }

    @Query("DELETE FROM routine_tasks_note WHERE id = :noteId")
    protected abstract fun deleteRoutineTasksOnly(noteId: Int)

    @Insert
    protected abstract fun insertRoutineTasksNote(note: RoutineTasksNoteEntity): Long

    @Insert
    protected abstract fun insertRoutineTasksNoteSubNote(item: RoutineTasksNoteSubNoteEntity)

    @Query("SELECT * FROM routine_tasks_note WHERE rowid=:rowId")
    protected abstract fun getRoutineTasksNoteByRowId(rowId: Long): RoutineTasksNoteEntity

    @Transaction
    open fun insert(note: Note.RoutineTasks) {
        val rowId = insertRoutineTasksNote(RoutineTasksNoteEntity())
        val routineTasksNote = getRoutineTasksNoteByRowId(rowId)
        note.active.forEachIndexed { index, item ->
            insertRoutineTasksNoteSubNote(
                RoutineTasksNoteSubNoteEntity(
                    noteId = routineTasksNote.id,
                    itemIndex = index,
                    completed = false,
                    title = item.title,
                    text = item.text
                )
            )
        }
    }

    @Query(
        "SELECT routine_tasks_note.*" +
                "FROM routine_tasks_note " +
                "LEFT JOIN  finished_notes " +
                "ON routine_tasks_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null"
    )
    protected abstract fun getAllRoutineTasksNotesGen(type: NoteType = NoteType.RoutineTasks): Flow<List<RoutineTasksNoteEntityWithSubNotes>>

    fun getAllRoutineTasksNotes() = getAllRoutineTasksNotesGen()

    @Query(
        "SELECT routine_tasks_note.*" +
                "FROM routine_tasks_note " +
                "LEFT JOIN  finished_notes " +
                "ON routine_tasks_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null " +
                "ORDER BY id DESC LIMIT 10"
    )
    protected abstract fun getLast10RoutineTasksNotesGen(type: NoteType = NoteType.RoutineTasks): Flow<List<RoutineTasksNoteEntityWithSubNotes>>

    fun getLast10RoutineTasksNotes() = getLast10RoutineTasksNotesGen()
}