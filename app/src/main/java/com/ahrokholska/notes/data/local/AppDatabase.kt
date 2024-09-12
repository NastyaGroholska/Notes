package com.ahrokholska.notes.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.ahrokholska.notes.data.local.dao.BuySomethingNotesDao
import com.ahrokholska.notes.data.local.dao.FinishNoteDao
import com.ahrokholska.notes.data.local.dao.InterestingIdeaNotesDao
import com.ahrokholska.notes.data.local.dao.PinNoteDao
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteEntity
import com.ahrokholska.notes.data.local.entities.BuySomethingNoteItem
import com.ahrokholska.notes.data.local.entities.FinishedNoteEntity
import com.ahrokholska.notes.data.local.entities.InterestingIdeaNoteEntity
import com.ahrokholska.notes.data.local.entities.PinnedNoteEntity

@Database(
    entities = [InterestingIdeaNoteEntity::class, FinishedNoteEntity::class, PinnedNoteEntity::class,
        BuySomethingNoteEntity::class, BuySomethingNoteItem::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AppDatabase.AutoMigration1::class),
        AutoMigration(from = 2, to = 3), AutoMigration(from = 3, to = 4)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun interestingIdeaDao(): InterestingIdeaNotesDao
    abstract fun buySomethingNotesDao(): BuySomethingNotesDao
    abstract fun pinNoteDao(): PinNoteDao
    abstract fun finishNoteDao(): FinishNoteDao

    @DeleteColumn(tableName = "interesting_idea_note", columnName = "is_finished")
    @DeleteColumn(tableName = "interesting_idea_note", columnName = "is_pinned")
    class AutoMigration1 : AutoMigrationSpec
}