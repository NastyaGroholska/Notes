package com.ahrokholska.notes.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ahrokholska.notes.data.local.AppDatabase
import com.ahrokholska.notes.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BuySomethingNotesDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: BuySomethingNotesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.buySomethingNotesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun noteIsInserted() = runTest {
        val note = Note.BuyingSomething(
            title = "note1",
            items = listOf(false to "item1", false to "item2")
        )
        dao.insert(note)
        val allNotes = dao.getAllBuySomethingNotes().first()
        assertEquals(1, allNotes.size)
        assertEquals(note.title, allNotes.first().note.title)
        assertEquals(note.items.size, allNotes.first().items.size)
        note.items.forEach { item ->
            assert(allNotes.first().items.any { it.text == item.second && it.checked == item.first })
        }
    }
}