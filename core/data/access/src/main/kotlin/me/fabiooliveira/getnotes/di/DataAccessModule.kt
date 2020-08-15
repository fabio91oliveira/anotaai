package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.base.Mapper
import me.fabiooliveira.getnotes.data.mapper.NotesPageMapper
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.data.repository.impl.NoteRepositoryImpl
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DataAccessModule {
    private val accessModule = module {
        factory<Mapper<NoteEntity, Note>>(qualifier = named(NotesPageMapper::class.java.name)) {
            NotesPageMapper()
        }
        factory<NoteRepository> {
            NoteRepositoryImpl(
                    localDataSource = get(),
                    notesPageMapper = get(named(NotesPageMapper::class.java.name))
            )
        }
    }

    fun load() {
        DataLocalModule.load()
        loadKoinModules(accessModule)
    }
}