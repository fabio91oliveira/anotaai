package me.fabiooliveira.anotaai.di.module

import dagger.Module
import dagger.Provides
import me.fabiooliveira.anotaai.model.repository.LogRepository
import me.fabiooliveira.anotaai.persistence.dao.NoteDao
import me.fabiooliveira.anotaai.model.repository.NoteRepository
import me.fabiooliveira.anotaai.persistence.dao.LogDao
import javax.inject.Singleton

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
    @Provides
    @Singleton
    fun provideLogRepository(logDao: LogDao): LogRepository {
        return LogRepository(logDao)
    }
}