package com.ahrokholska.notes.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.ahrokholska.notes.data.local.AppDatabase
import com.ahrokholska.notes.data.repository.NotesRepositoryImpl
import com.ahrokholska.notes.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    @Singleton
    fun provideInterestingIdeaNotesDao(db: AppDatabase) = db.interestingIdeaDao()

    @Provides
    @Singleton
    fun provideBuySomethingNotesDao(db: AppDatabase) = db.buySomethingNotesDao()

    @Provides
    @Singleton
    fun provideGoalsNotesDao(db: AppDatabase) = db.goalsNotesDao()

    @Provides
    @Singleton
    fun provideGuidanceNotesDao(db: AppDatabase) = db.guidanceNotesDao()

    @Provides
    @Singleton
    fun provideRoutineTasksNotesDao(db: AppDatabase) = db.routineTasksNotesDao()

    @Provides
    @Singleton
    fun providePinNoteDao(db: AppDatabase) = db.pinNoteDao()

    @Provides
    @Singleton
    fun provideFinishNoteDao(db: AppDatabase) = db.finishNoteDao()

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppBindModule {
        @Binds
        @Singleton
        fun bindNotesRepository(notesRepository: NotesRepositoryImpl): NotesRepository
    }
}