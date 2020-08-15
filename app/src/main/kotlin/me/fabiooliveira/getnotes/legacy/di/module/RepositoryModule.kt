//package me.fabiooliveira.getnotes.legacy.di.module
//
//import dagger.Module
//import dagger.Provides
//import me.fabiooliveira.getnotes.legacy.model.repository.LogRepository
//import me.fabiooliveira.getnotes.legacy.persistence.dao.NoteDao
//import me.fabiooliveira.getnotes.legacy.model.repository.NoteRepository
//import me.fabiooliveira.getnotes.legacy.persistence.dao.LogDao
//import javax.inject.Singleton
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//@Module
//class RepositoryModule {
//    @Provides
//    @Singleton
//    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
//        return NoteRepository(noteDao)
//    }
//    @Provides
//    @Singleton
//    fun provideLogRepository(logDao: LogDao): LogRepository {
//        return LogRepository(logDao)
//    }
//}