package com.ahrokholska.notes.di

import android.content.Context
import androidx.room.Room
import com.ahrokholska.notes.data.local.AppDatabase
import com.ahrokholska.notes.data.repository.NotesRepositoryImpl
import com.ahrokholska.notes.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    fun provideNotesDao(db: AppDatabase) = db.notesDao()

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppBindModule {
        @Binds
        fun bindNotesRepository(notesRepository: NotesRepositoryImpl): NotesRepository
    }
}