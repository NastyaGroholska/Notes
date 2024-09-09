package com.ahrokholska.notes.data.repository

import com.ahrokholska.notes.data.local.NotesDao
import com.ahrokholska.notes.data.mapper.toDomain
import com.ahrokholska.notes.data.mapper.toEntity
import com.ahrokholska.notes.domain.model.Note
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
class NotesRepositoryImpl @Inject constructor(private val notesDao: NotesDao) : NotesRepository {
    override suspend fun saveNote(note: Note): Result<Unit> = withContext(Dispatchers.IO) {
        when (note) {
            is Note.InterestingIdea -> {
                getResult { notesDao.insertInterestingIdeaNote(note.toEntity()) }
            }

            is Note.BuyingSomething -> TODO()
            is Note.Goals -> TODO()
            is Note.Guidance -> TODO()
            is Note.RoutineTasks -> TODO()
        }
    }

    override suspend fun deleteNote(noteId: Int, noteType: KClass<Note>): Result<Unit> =
        withContext(Dispatchers.IO) {
            when (noteType) {
                Note.InterestingIdea::class -> {
                    getResult { notesDao.deleteInterestingIdeaNote(noteId) }
                }

                Note.BuyingSomething::class -> TODO()
                Note.Goals::class -> TODO()
                Note.Guidance::class -> TODO()
                Note.RoutineTasks::class -> TODO()
                else -> Result.failure(Throwable("incorrect type"))
            }
        }

    override fun getAllInterestingIdeaNotes(): Flow<List<Note.InterestingIdea>> =
        notesDao.getAllInterestingIdeaNotes().map { list -> list.map { it.toDomain() } }

    override fun getLast10InterestingIdeaNotes(): Flow<List<Note.InterestingIdea>> =
        notesDao.getLast10InterestingIdeaNotes().map { list -> list.map { it.toDomain() } }
}