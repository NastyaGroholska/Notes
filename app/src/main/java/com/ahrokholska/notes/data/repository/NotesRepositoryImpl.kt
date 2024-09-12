package com.ahrokholska.notes.data.repository

import com.ahrokholska.notes.data.local.dao.BuySomethingNotesDao
import com.ahrokholska.notes.data.local.dao.FinishNoteDao
import com.ahrokholska.notes.data.local.dao.InterestingIdeaNotesDao
import com.ahrokholska.notes.data.local.dao.PinNoteDao
import com.ahrokholska.notes.data.mapper.toDomainPreview
import com.ahrokholska.notes.data.mapper.toEntity
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
    private val pinNoteDao: PinNoteDao,
    private val finishNoteDao: FinishNoteDao,
) : NotesRepository {
    override suspend fun saveNote(note: Note): Result<Unit> = withContext(Dispatchers.IO) {
        when (note) {
            is Note.InterestingIdea ->
                getResult { interestingIdeaNotesDao.insert(note.toEntity()) }

            is Note.BuyingSomething ->
                getResult { buySomethingNotesDao.insert(note) }

            is Note.Goals -> TODO()
            is Note.Guidance -> TODO()
            is Note.RoutineTasks -> TODO()
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

                Note.Goals::class -> TODO()
                Note.Guidance::class -> TODO()
                Note.RoutineTasks::class -> TODO()
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
}