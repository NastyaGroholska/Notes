package com.ahrokholska.notes.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ahrokholska.notes.data.local.AppDatabase
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.data.local.entities.PinnedNoteEntity
import com.ahrokholska.notes.domain.model.NoteType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class InterestingIdeaNotesDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: InterestingIdeaNotesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.interestingIdeaDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun noteIsInserted() = runTest {
        val note = InterestingIdeaNoteEntity(title = "note1", body = "body1")
        dao.insert(note)
        val allNotes = dao.getAllInterestingIdeaNotes().first()
        assertEquals(1, allNotes.size)
        assertEquals(note.title, allNotes.first().title)
        assertEquals(note.body, allNotes.first().body)
    }

    @Test
    fun noteIsDeleted() = runTest {
        val note = InterestingIdeaNoteEntity(title = "note1", body = "body1")
        dao.insert(note)
        var allNotes = dao.getAllInterestingIdeaNotes().first()
        dao.delete(allNotes.first().id, {}, {})
        allNotes = dao.getAllInterestingIdeaNotes().first()
        assertEquals(0, allNotes.size)
    }

    @Test
    fun onlyCorrectNoteIsDeleted() = runTest {
        val note1 = InterestingIdeaNoteEntity(title = "note1", body = "body1")
        val note2 = InterestingIdeaNoteEntity(title = "note2", body = "body2")
        val note3 = InterestingIdeaNoteEntity(title = "note3", body = "body3")
        dao.insert(note1)
        dao.insert(note2)
        dao.insert(note3)
        var allNotes = dao.getAllInterestingIdeaNotes().first()
        val note1Id =
            allNotes.find { it.title == note1.title }?.id ?: throw Error("note was not inserted")
        dao.delete(note1Id, {}, {})
        allNotes = dao.getAllInterestingIdeaNotes().first()
        assertEquals(2, allNotes.size)
        assert(allNotes.all { it.title != note1.title })
    }

    @Test
    fun getLast10ReturnsCorrectList() = runTest {
        val note0 = InterestingIdeaNoteEntity(title = "note0", body = "body0")
        val tenNotes =
            List(10) { InterestingIdeaNoteEntity(title = "note${it + 1}", body = "body${it + 1}") }
        dao.insert(note0)
        tenNotes.forEach {
            dao.insert(it)
        }
        val last10Notes = dao.getLast10InterestingIdeaNotes().first()
        assertEquals(10, last10Notes.size)
        last10Notes.reversed().forEachIndexed { index, note ->
            assertEquals(tenNotes[index].title, note.title)
            assertEquals(tenNotes[index].body, note.body)
        }
    }

    @Test
    fun getNoteWithoutDetailsIsCorrect() = runTest {
        val note = InterestingIdeaNoteEntity(title = "note1", body = "body1")
        dao.insert(note)
        val noteId = dao.getAllInterestingIdeaNotes().first().first().id
        val noteDetails = dao.getInterestingIdeaNoteDetails(noteId).first()
            ?: throw Error("note details does not exist")
        assertEquals(note.title, noteDetails.note.title)
        assertEquals(note.body, noteDetails.note.body)
        assertEquals(false, noteDetails.isPinned)
        assertEquals(false, noteDetails.isFinished)
    }

    @Test
    fun getPinnedNoteDetailsIsCorrect() = runTest {
        val note = InterestingIdeaNoteEntity(title = "note1", body = "body1")
        dao.insert(note)
        val noteId = dao.getAllInterestingIdeaNotes().first().first().id
        database.pinNoteDao()
            .pinNote(PinnedNoteEntity(noteId, NoteType.InterestingIdea, System.currentTimeMillis()))
        val noteDetails = dao.getInterestingIdeaNoteDetails(noteId).first()
            ?: throw Error("note details does not exist")
        assertEquals(note.title, noteDetails.note.title)
        assertEquals(note.body, noteDetails.note.body)
        assertEquals(true, noteDetails.isPinned)
        assertEquals(false, noteDetails.isFinished)
    }

    @Test
    fun getFinishedNoteDetailsIsCorrect() = runTest {
        val note = InterestingIdeaNoteEntity(title = "note1", body = "body1")
        dao.insert(note)
        val noteId = dao.getAllInterestingIdeaNotes().first().first().id
        database.finishNoteDao()
            .finishNote(noteId, NoteType.InterestingIdea, System.currentTimeMillis(), {})
        val noteDetails = dao.getInterestingIdeaNoteDetails(noteId).first()
            ?: throw Error("note details does not exist")
        assertEquals(note.title, noteDetails.note.title)
        assertEquals(note.body, noteDetails.note.body)
        assertEquals(false, noteDetails.isPinned)
        assertEquals(true, noteDetails.isFinished)
    }

    @Test
    fun getNoteByIdIsCorrect() = runTest {
        val tenNotes = List(10) { InterestingIdeaNoteEntity(title = "note$it", body = "body$it") }
        tenNotes.forEach {
            dao.insert(it)
        }
        val targetNoteTitle = "note1"
        val targetNoteBody = "body1"
        val noteId = (dao.getAllInterestingIdeaNotes().first().find { it.title == targetNoteTitle }
            ?: throw Error("note wasn't inserted")).id
        val note = dao.getInterestingIdeaNote(noteId).first()
        assertEquals(targetNoteTitle, note.title)
        assertEquals(targetNoteBody, note.body)
    }

    @Test
    fun getNoteTitlesIsCorrect() = runTest {
        val tenNotes = List(10) { InterestingIdeaNoteEntity(title = "note$it", body = "body$it") }
        tenNotes.forEach {
            dao.insert(it)
        }
        val titles = dao.getAllTitles().first()
        assertEquals(tenNotes.size, titles.size)
        tenNotes.forEach { note ->
            assert(titles.any { it.title == note.title })
        }
    }
}