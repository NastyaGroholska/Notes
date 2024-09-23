package com.ahrokholska.notes.data.repository

import com.ahrokholska.notes.data.local.dao.BuySomethingNotesDao
import com.ahrokholska.notes.data.local.dao.FinishNoteDao
import com.ahrokholska.notes.data.local.dao.GoalsNotesDao
import com.ahrokholska.notes.data.local.dao.GuidanceNotesDao
import com.ahrokholska.notes.data.local.dao.InterestingIdeaNotesDao
import com.ahrokholska.notes.data.local.dao.PinNoteDao
import com.ahrokholska.notes.data.local.dao.RoutineTasksNotesDao
import com.ahrokholska.notes.data.mapper.toDomainPreview
import com.ahrokholska.notes.data.mapper.toEntity
import com.ahrokholska.notes.data.mapper.toNote
import com.ahrokholska.notes.domain.model.Note
import com.ahrokholska.notes.domain.model.NotePreview
import com.ahrokholska.notes.domain.model.NoteType
import com.ahrokholska.notes.domain.repository.NotesRepository
import com.ahrokholska.notes.utils.ResultUtils.getResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
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

    override suspend fun deleteNote(noteId: Int, noteType: KClass<Note>): Result<Unit> =
        withContext(Dispatchers.IO) {
            when (noteType) {
                Note.InterestingIdea::class -> getResult {
                    interestingIdeaNotesDao.delete(noteId,
                        { pinNoteDao.unpinNote(noteId, NoteType.InterestingIdea) },
                        { finishNoteDao.deleteFinishedRecord(noteId, NoteType.InterestingIdea) }
                    )
                }

                Note.BuyingSomething::class -> getResult {
                    buySomethingNotesDao.delete(noteId,
                        { pinNoteDao.unpinNote(noteId, NoteType.BuyingSomething) },
                        { finishNoteDao.deleteFinishedRecord(noteId, NoteType.BuyingSomething) }
                    )
                }

                Note.Goals::class -> getResult {
                    goalsNotesDao.delete(noteId,
                        { pinNoteDao.unpinNote(noteId, NoteType.Goals) },
                        { finishNoteDao.deleteFinishedRecord(noteId, NoteType.Goals) }
                    )
                }

                Note.Guidance::class -> getResult {
                    guidanceNotesDao.delete(noteId,
                        { pinNoteDao.unpinNote(noteId, NoteType.Guidance) },
                        { finishNoteDao.deleteFinishedRecord(noteId, NoteType.Guidance) }
                    )
                }

                Note.RoutineTasks::class -> getResult {
                    routineTasksNotesDao.delete(noteId,
                        { pinNoteDao.unpinNote(noteId, NoteType.RoutineTasks) },
                        { finishNoteDao.deleteFinishedRecord(noteId, NoteType.RoutineTasks) }
                    )
                }

                else -> Result.failure(Throwable("incorrect type"))
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

    override fun getInterestingIdeaNoteDetails(noteId: Int): Flow<Note.InterestingIdea> =
        interestingIdeaNotesDao.getInterestingIdeaNoteDetails(noteId).map { it.toNote() }

}