package com.ahrokholska.room

import android.content.Context
import androidx.room.Room
import com.ahrokholska.api.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    internal fun provideRoomDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    @Singleton
    internal fun provideInterestingIdeaNotesDao(db: AppDatabase) = db.interestingIdeaDao()

    @Provides
    @Singleton
    internal fun provideBuySomethingNotesDao(db: AppDatabase) = db.buySomethingNotesDao()

    @Provides
    @Singleton
    internal fun provideGoalsNotesDao(db: AppDatabase) = db.goalsNotesDao()

    @Provides
    @Singleton
    internal fun provideGuidanceNotesDao(db: AppDatabase) = db.guidanceNotesDao()

    @Provides
    @Singleton
    internal fun provideRoutineTasksNotesDao(db: AppDatabase) = db.routineTasksNotesDao()

    @Provides
    @Singleton
    internal fun providePinNoteDao(db: AppDatabase) = db.pinNoteDao()

    @Provides
    @Singleton
    internal fun provideFinishNoteDao(db: AppDatabase) = db.finishNoteDao()

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppBindModule {
        @Binds
        @Singleton
        fun bindNotesRepository(notesRepository: NotesRepositoryImpl): NotesRepository
    }
}