//package me.fabiooliveira.getnotes.legacy.di.module
//
//import androidx.lifecycle.ViewModelProvider
//import dagger.Module
//import dagger.Provides
//import me.fabiooliveira.getnotes.legacy.model.factory.NoteViewModelFactory
//import me.fabiooliveira.getnotes.legacy.feature.notesList.viewModel.mapper.NoteMapper
//import me.fabiooliveira.getnotes.legacy.model.repository.LogRepository
//import me.fabiooliveira.getnotes.legacy.model.repository.NoteRepository
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
//class ViewModelFactoryModule {
//    @Provides
//    @Singleton
//    fun provideNoteViewModelFactory(noteRepository: NoteRepository, logRepository: LogRepository,
//                                    noteMapper: NoteMapper): ViewModelProvider.Factory {
//        return NoteViewModelFactory(noteRepository, logRepository, noteMapper)
//    }
//}