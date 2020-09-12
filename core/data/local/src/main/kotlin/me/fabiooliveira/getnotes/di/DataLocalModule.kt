package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.config.Database
import me.fabiooliveira.getnotes.config.provideBuilder
import me.fabiooliveira.getnotes.datasource.NoteLocalDataSource
import me.fabiooliveira.getnotes.datasource.impl.NoteLocalDataSourceImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataLocalModule {
    private val daoModule = module {
        factory {
            provideBuilder(
                    context = get()
            )
        }
        factory {
            get<Database>().noteDao()
        }
    }

    private val localDataSourceModule = module {
        factory<NoteLocalDataSource> {
            NoteLocalDataSourceImpl(
                    noteDao = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(daoModule, localDataSourceModule))
}