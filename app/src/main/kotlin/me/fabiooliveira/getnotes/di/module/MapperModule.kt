package me.fabiooliveira.getnotes.di.module

import dagger.Module
import dagger.Provides
import me.fabiooliveira.getnotes.feature.notesList.viewModel.mapper.NoteMapper
import javax.inject.Singleton

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Module
class MapperModule {
    @Provides
    @Singleton
    fun provideNoteMapper(): NoteMapper {
        return NoteMapper()
    }
}