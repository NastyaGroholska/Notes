package com.ahrokholska.notes.data.repository

import com.ahrokholska.notes.data.local.dao.BuySomethingNotesDao
import com.ahrokholska.notes.data.local.dao.FinishNoteDao
import com.ahrokholska.notes.data.local.dao.GoalsNotesDao
import com.ahrokholska.notes.data.local.dao.GuidanceNotesDao
import com.ahrokholska.notes.data.local.dao.InterestingIdeaNotesDao
import com.ahrokholska.notes.data.local.dao.PinNoteDao
import com.ahrokholska.notes.data.local.dao.RoutineTasksNotesDao
import com.ahrokholska.notes.data.local.entities.PinnedNoteEntity
import com.ahrokholska.notes.data.mapper.toDomainPreview
import com.ahrokholska.notes.data.mapper.toEntity
import com.ahrokholska.notes.data.mapper.toNote
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NotePreview
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.repository.NotesRepository
import com.ahrokholska.notes.utils.ResultUtils.getResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val interestingIdeaNotesDao: InterestingIdeaNotesDao,
    private val buySomethingNotesDao: BuySomethingNotesDao,
    private val goalsNotesDao: GoalsNotesDao,
    private val guidanceNotesDao: GuidanceNotesDao,
    private val routineTasksNotesDao: RoutineTasksNotesDao,
    private val pinNoteDao: PinNoteDao,
    private val finishNoteDao: FinishNoteDao,
) : NotesRepository {
    override suspend fun saveNote(note: Note): Result<Unit> = withContext(Dispatchers.IO) {
        when (note) {
            is Note.InterestingIdea ->
                getResult { interestingIdeaNotesDao.insert(note.toEntity()) }

            is Note.BuyingSomething ->
                getResult { buySomethingNotesDao.insert(note) }

            is Note.Goals -> getResult { goalsNotesDao.insert(note) }
            is Note.Guidance -> getResult { guidanceNotesDao.insert(note.toEntity()) }
            is Note.RoutineTasks -> getResult { routineTasksNotesDao.insert(note) }
        }
    }

    override suspend fun deleteNote(noteId: Int, noteType: NoteType): Result<Unit> =
        withContext(Dispatchers.IO) {
            val unpinNote = { pinNoteDao.unpinNote(noteId, noteType) }
            val deleteFinishedRecord = { finishNoteDao.deleteFinishedRecord(noteId, noteType) }
            when (noteType) {
                NoteType.InterestingIdea -> getResult {
                    interestingIdeaNotesDao.delete(noteId, unpinNote, deleteFinishedRecord)
                }

                NoteType.BuyingSomething -> getResult {
                    buySomethingNotesDao.delete(noteId, unpinNote, deleteFinishedRecord)
                }

                NoteType.Goals -> getResult {
                    goalsNotesDao.delete(noteId, unpinNote, deleteFinishedRecord)
                }

                NoteType.Guidance -> getResult {
                    guidanceNotesDao.delete(noteId, unpinNote, deleteFinishedRecord)
                }

                NoteType.RoutineTasks -> getResult {
                    routineTasksNotesDao.delete(noteId, unpinNote, deleteFinishedRecord)
                }
            }
        }

    override fun getAllInterestingIdeaNotes(): Flow<List<NotePreview.InterestingIdea>> =
        interestingIdeaNotesDao.getAllInterestingIdeaNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getLast10InterestingIdeaNotes(): Flow<List<NotePreview.InterestingIdea>> =
        interestingIdeaNotesDao.getLast10InterestingIdeaNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getAllBuySomethingNotes(): Flow<List<NotePreview.BuyingSomething>> =
        buySomethingNotesDao.getAllBuySomethingNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getLast10BuySomethingNotes(): Flow<List<NotePreview.BuyingSomething>> =
        buySomethingNotesDao.getLast10BuySomethingNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getAllGoalsNotes(): Flow<List<NotePreview.Goals>> =
        goalsNotesDao.getAllGoalsNotes().map { map ->
            map.map { mapEntry -> mapEntry.toDomainPreview() }
        }

    override fun getLast10GoalsNotes(): Flow<List<NotePreview.Goals>> =
        goalsNotesDao.getLast10GoalsNotes().map { map ->
            map.map { mapEntry -> mapEntry.toDomainPreview() }
        }

    override fun getAllGuidanceNotes(): Flow<List<NotePreview.Guidance>> =
        guidanceNotesDao.getAllGuidanceNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getLast10GuidanceNotes(): Flow<List<NotePreview.Guidance>> =
        guidanceNotesDao.getLast10GuidanceNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getAllRoutineTasksNotes(): Flow<List<NotePreview.RoutineTasks>> =
        routineTasksNotesDao.getAllRoutineTasksNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getLast10RoutineTasksNotes(): Flow<List<NotePreview.RoutineTasks>> =
        routineTasksNotesDao.getLast10RoutineTasksNotes().map { list ->
            list.map { it.toDomainPreview() }
        }

    override fun getInterestingIdeaNoteDetails(noteId: Int): Flow<Note.InterestingIdea?> =
        interestingIdeaNotesDao.getInterestingIdeaNoteDetails(noteId).map { it?.toNote() }

    override fun getBuySomethingNoteDetails(noteId: Int): Flow<Note.BuyingSomething?> =
        buySomethingNotesDao.getBuySomethingNoteDetails(noteId).map { it?.toNote() }

    override fun getGoalsNoteDetails(noteId: Int): Flow<Note.Goals?> =
        goalsNotesDao.getGoalsNoteDetails(noteId)
            .combine(goalsNotesDao.getGoalsNoteTasks(noteId)) { detail, tasks ->
                detail?.toNote(tasks)
            }

    override fun getGuidanceNoteDetails(noteId: Int): Flow<Note.Guidance?> =
        guidanceNotesDao.getGuidanceNoteDetails(noteId).map { it?.toNote() }

    override fun getRoutineTasksNoteDetails(noteId: Int): Flow<Note.RoutineTasks?> =
        routineTasksNotesDao.getRoutineTasksNoteDetails(noteId).map { it?.toNote() }

    override suspend fun changeBuySomethingItemCheck(
        noteId: Int, index: Int, checked: Boolean
    ): Result<Unit> = withContext(Dispatchers.IO) {
        getResult { buySomethingNotesDao.changeCheck(noteId, index, checked) }
    }

    override suspend fun changeGoalsTaskCheck(
        noteId: Int, index: Int, checked: Boolean
    ): Result<Unit> = withContext(Dispatchers.IO) {
        getResult { goalsNotesDao.changeTaskCheck(noteId, index, checked) }
    }

    override suspend fun changeGoalsSubtaskCheck(
        noteId: Int, taskIndex: Int, subtaskIndex: Int, checked: Boolean
    ): Result<Unit> = withContext(Dispatchers.IO) {
        getResult { goalsNotesDao.changeSubtaskCheck(noteId, taskIndex, subtaskIndex, checked) }
    }

    override suspend fun changeRoutineTasksSubNoteCheck(
        noteId: Int, index: Int, finished: Boolean
    ): Result<Unit> = withContext(Dispatchers.IO) {
        getResult { routineTasksNotesDao.changeRoutineTasksSubNoteCheck(noteId, index, finished) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLast10PinnedNotes(): Flow<List<NotePreview>> =
        pinNoteDao.getLast10PinnedNotes().flatMapLatest { list ->
            if (list.isEmpty()) {
                flowOf(listOf())
            } else {
                combine(list.map { note -> mapNote(note.noteType, note.noteId) }) { it.asList() }
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllPinnedNotes(): Flow<List<NotePreview>> =
        pinNoteDao.getAllPinnedNotes().flatMapLatest { list ->
            if (list.isEmpty()) {
                flowOf(listOf())
            } else {
                combine(list.map { note -> mapNote(note.noteType, note.noteId) }) { it.asList() }
            }
        }

    private fun mapNote(noteType: NoteType, noteId: Int) = when (noteType) {
        NoteType.InterestingIdea ->
            interestingIdeaNotesDao.getInterestingIdeaNote(noteId)
                .map { it.toDomainPreview() }

        NoteType.BuyingSomething ->
            buySomethingNotesDao.getBuySomethingNote(noteId)
                .map { it.toDomainPreview() }

        NoteType.Goals ->
            goalsNotesDao.getGoalsNote(noteId)
                .map { map ->
                    map.entries.first().toDomainPreview()
                }

        NoteType.Guidance -> guidanceNotesDao.getGuidanceNote(noteId)
            .map { it.toDomainPreview() }

        NoteType.RoutineTasks -> routineTasksNotesDao.getRoutineTasksNote(noteId)
            .map { it.toDomainPreview() }
    }

    override suspend fun pinNote(noteId: Int, noteType: NoteType, time: Long) =
        withContext(Dispatchers.IO) {
            pinNoteDao.pinNote(PinnedNoteEntity(noteId, noteType, time))
        }

    override suspend fun unpinNote(noteId: Int, noteType: NoteType) = withContext(Dispatchers.IO) {
        pinNoteDao.unpinNote(noteId, noteType)
    }

    override suspend fun finishNote(noteId: Int, noteType: NoteType, time: Long) =
        withContext(Dispatchers.IO) {
            finishNoteDao.finishNote(noteId, noteType, time) {
                pinNoteDao.unpinNote(noteId, noteType)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllFinishedNotes(): Flow<List<NotePreview>> =
        finishNoteDao.getAllFinishedNotes().flatMapLatest { list ->
            if (list.isEmpty()) {
                flowOf(listOf())
            } else {
                combine(list.map { note -> mapNote(note.noteType, note.noteId) }) { it.asList() }
            }
        }
}