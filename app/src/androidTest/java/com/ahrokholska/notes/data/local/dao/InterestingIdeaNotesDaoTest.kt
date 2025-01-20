package com.ahrokholska.notes.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ahrokholska.notes.data.local.AppDatabase
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
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
}