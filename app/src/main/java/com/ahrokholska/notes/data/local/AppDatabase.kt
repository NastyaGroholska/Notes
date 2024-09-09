package com.ahrokholska.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity

@Database(entities = [InterestingIdeaNoteEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}