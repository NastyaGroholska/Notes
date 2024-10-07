package com.ahrokholska.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.notes.data.local.entities.GoalsNoteEntity
import com.ahrokholska.notes.data.local.entities.GoalsNoteSubtaskEntity
import com.ahrokholska.notes.data.local.entities.GoalsNoteTaskEntity
import com.ahrokholska.notes.data.local.intermediate.GoalsNoteDetails
import com.ahrokholska.notes.data.local.intermediate.TaskAndSubtask
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GoalsNotesDao {
    @Transaction
    open fun delete(
        noteId: Int, unpinNote: () -> Unit,
        deleteFinishedRecord: () -> Unit
    ) {
        deleteGoalsOnly(noteId)
        unpinNote()
        deleteFinishedRecord()
    }

    @Query("DELETE FROM goals_note WHERE id = :noteId")
    protected abstract fun deleteGoalsOnly(noteId: Int)

    @Insert
    protected abstract fun insertGoalsNoteEntity(note: GoalsNoteEntity): Long

    @Query("SELECT * FROM goals_note WHERE rowid=:rowId")
    protected abstract fun getGoalsNoteEntityByRowId(rowId: Long): GoalsNoteEntity

    @Insert
    protected abstract fun insertGoalsNoteTaskEntity(task: GoalsNoteTaskEntity)

    @Insert
    protected abstract fun insertGoalsNoteSubtaskEntity(subtask: GoalsNoteSubtaskEntity)

    @Transaction
    open fun insert(note: Note.Goals) {
        val rowId = insertGoalsNoteEntity(GoalsNoteEntity(title = note.title))
        val goalsNote = getGoalsNoteEntityByRowId(rowId)
        note.tasks.forEachIndexed { taskIndex, task ->
            insertGoalsNoteTaskEntity(
                GoalsNoteTaskEntity(
                    noteId = goalsNote.id,
                    taskIndex = taskIndex,
                    checked = task.first.finished,
                    text = task.first.text
                )
            )
            task.second.forEachIndexed { subtaskIndex, subtask ->
                insertGoalsNoteSubtaskEntity(
                    GoalsNoteSubtaskEntity(
                        noteId = goalsNote.id,
                        taskIndex = taskIndex,
                        subtaskIndex = subtaskIndex,
                        checked = subtask.finished,
                        text = subtask.text
                    )
                )
            }
        }
    }

    @Query(
        "SELECT goals_note.*, goals_note_task.task_index, goals_note_task.checked as task_checked, goals_note_task.text as task_text," +
                "goals_note_subtask.checked as subtask_checked, goals_note_subtask.text as subtask_text " +
                "FROM goals_note " +
                "JOIN goals_note_task ON goals_note.id = goals_note_task.note_id " +
                "LEFT JOIN goals_note_subtask ON goals_note_task.note_id = goals_note_subtask.note_id " +
                "AND goals_note_task.task_index = goals_note_subtask.task_index " +
                "LEFT JOIN  finished_notes " +
                "ON goals_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null"
    )
    protected abstract fun getAllGoalsNotesGen(type: NoteType = NoteType.Goals)
            : Flow<Map<GoalsNoteEntity, List<TaskAndSubtask>>>

    fun getAllGoalsNotes() = getAllGoalsNotesGen()

    @Query(
        "SELECT goals_note.*, goals_note_task.task_index, goals_note_task.checked as task_checked, goals_note_task.text as task_text," +
                "goals_note_subtask.checked as subtask_checked, goals_note_subtask.text as subtask_text " +
                "FROM goals_note " +
                "JOIN goals_note_task ON goals_note.id = goals_note_task.note_id " +
                "LEFT JOIN goals_note_subtask ON goals_note_task.note_id = goals_note_subtask.note_id " +
                "AND goals_note_task.task_index = goals_note_subtask.task_index " +
                "LEFT JOIN  finished_notes " +
                "ON goals_note.id = finished_notes.note_id AND finished_notes.note_type = :type " +
                "WHERE finished_notes.note_id is null " +
                "ORDER BY id DESC LIMIT 10"
    )
    protected abstract fun getLast10GoalsNotesGen(type: NoteType = NoteType.Goals)
            : Flow<Map<GoalsNoteEntity, List<TaskAndSubtask>>>

    fun getLast10GoalsNotes() = getLast10GoalsNotesGen()

    @Query(
        "SELECT goals_note_task.task_index, goals_note_task.checked as task_checked, goals_note_task.text as task_text," +
                "goals_note_subtask.checked as subtask_checked, goals_note_subtask.text as subtask_text " +
                "FROM goals_note_task " +
                "LEFT JOIN goals_note_subtask ON goals_note_task.note_id = goals_note_subtask.note_id " +
                "AND goals_note_task.task_index = goals_note_subtask.task_index " +
                "WHERE goals_note_task.note_id = :id "
    )
    abstract fun getGoalsNoteTasks(id: Int): Flow<List<TaskAndSubtask>>

    @Query(
        "SELECT goals_note.*, NOT finished_notes.note_id is NULL as isFinished,NOT pinned_notes.note_id is NULL as isPinned " +
                "FROM goals_note " +
                "LEFT JOIN finished_notes ON finished_notes.note_id = :id AND finished_notes.note_type = :type " +
                "LEFT JOIN pinned_notes ON pinned_notes.note_id = :id AND pinned_notes.note_type = :type " +
                "WHERE goals_note.id = :id"
    )
    protected abstract fun getGoalsNoteDetailsGen(
        id: Int, type: NoteType = NoteType.Goals
    ): Flow<GoalsNoteDetails>

    fun getGoalsNoteDetails(id: Int) = getGoalsNoteDetailsGen(id)

    @Query("UPDATE goals_note_task SET checked = :checked WHERE note_id = :id AND task_index = :index")
    abstract fun changeTaskCheck(id: Int, index: Int, checked: Boolean)

    @Query("UPDATE goals_note_subtask SET checked = :checked WHERE note_id = :id AND task_index = :taskIndex AND subtask_index = :subtaskIndex")
    abstract fun changeSubtaskCheck(id: Int, taskIndex: Int, subtaskIndex: Int, checked: Boolean)

    @Query(
        "SELECT goals_note.*, goals_note_task.task_index, goals_note_task.checked as task_checked, goals_note_task.text as task_text," +
                "goals_note_subtask.checked as subtask_checked, goals_note_subtask.text as subtask_text " +
                "FROM goals_note " +
                "JOIN goals_note_task ON goals_note.id = goals_note_task.note_id " +
                "LEFT JOIN goals_note_subtask ON goals_note_task.note_id = goals_note_subtask.note_id " +
                "AND goals_note_task.task_index = goals_note_subtask.task_index " +
                "WHERE goals_note.id = :id"
    )
    abstract fun getGoalsNote(id: Int): Flow<Map<GoalsNoteEntity, List<TaskAndSubtask>>>
}