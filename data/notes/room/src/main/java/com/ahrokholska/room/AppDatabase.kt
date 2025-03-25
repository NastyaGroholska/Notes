package com.ahrokholska.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.ahrokholska.room.dao.BuySomethingNotesDao
import com.ahrokholska.room.dao.FinishNoteDao
import com.ahrokholska.room.dao.GoalsNotesDao
import com.ahrokholska.room.dao.GuidanceNotesDao
import com.ahrokholska.room.dao.InterestingIdeaNotesDao
import com.ahrokholska.room.dao.PinNoteDao
import com.ahrokholska.room.dao.RoutineTasksNotesDao
import com.ahrokholska.room.entities.BuySomethingNoteEntity
import com.ahrokholska.room.entities.BuySomethingNoteItemEntity
import com.ahrokholska.room.entities.FinishedNoteEntity
import com.ahrokholska.room.entities.GoalsNoteEntity
import com.ahrokholska.room.entities.GoalsNoteSubtaskEntity
import com.ahrokholska.room.entities.GoalsNoteTaskEntity
import com.ahrokholska.room.entities.GuidanceNoteEntity
import com.ahrokholska.room.entities.InterestingIdeaNoteEntity
import com.ahrokholska.room.entities.PinnedNoteEntity
import com.ahrokholska.room.entities.RoutineTasksNoteEntity
import com.ahrokholska.room.entities.RoutineTasksNoteSubNoteEntity

@Database(
    entities = [InterestingIdeaNoteEntity::class, FinishedNoteEntity::class, PinnedNoteEntity::class,
        BuySomethingNoteEntity::class, BuySomethingNoteItemEntity::class,
        GoalsNoteEntity::class, GoalsNoteTaskEntity::class, GoalsNoteSubtaskEntity::class,
        GuidanceNoteEntity::class,
        RoutineTasksNoteEntity::class, RoutineTasksNoteSubNoteEntity::class],
    version = 9,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AppDatabase.AutoMigration1::class),
        AutoMigration(from = 2, to = 3), AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5), AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7), AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9)
    ]
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun interestingIdeaDao(): InterestingIdeaNotesDao
    abstract fun buySomethingNotesDao(): BuySomethingNotesDao
    abstract fun goalsNotesDao(): GoalsNotesDao
    abstract fun guidanceNotesDao(): GuidanceNotesDao
    abstract fun routineTasksNotesDao(): RoutineTasksNotesDao
    abstract fun pinNoteDao(): PinNoteDao
    abstract fun finishNoteDao(): FinishNoteDao

    @DeleteColumn(tableName = "interesting_idea_note", columnName = "is_finished")
    @DeleteColumn(tableName = "interesting_idea_note", columnName = "is_pinned")
    class AutoMigration1 : AutoMigrationSpec
}