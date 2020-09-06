package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.data.datasource.local.DarkModePreferencesLocalDataSource
import me.fabiooliveira.getnotes.data.datasource.local.impl.DarkModePreferencesLocalDataSourceImpl
import me.fabiooliveira.getnotes.data.repository.DarkModePreferencesRepositoryImpl
import me.fabiooliveira.getnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.domain.usecase.ChangeDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.domain.usecase.GetDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.domain.usecase.GetPastListNotesUseCase
import me.fabiooliveira.getnotes.domain.usecase.GetRecentListNotesUseCase
import me.fabiooliveira.getnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.domain.usecase.impl.ChangeDarkModePreferencesUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.GetDarkModePreferencesUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.GetPastListNotesUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.GetRecentListNotesUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.MountNoteItemsUseCaseImpl
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.presentation.navigation.ListNotesNavigationImpl
import me.fabiooliveira.getnotes.presentation.viewmodel.ListNotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ListNotesModule {
    private val dataModule = module {
        factory<DarkModePreferencesLocalDataSource> {
            DarkModePreferencesLocalDataSourceImpl(
                    sharePreferences = get(),
                    sharePreferencesEditor = get()
            )
        }
        factory<DarkModePreferencesRepository> {
            DarkModePreferencesRepositoryImpl(
                    darkModePreferencesLocalDataSource = get()
            )
        }
    }
    private val domainModule = module {
        factory<GetRecentListNotesUseCase> {
            GetRecentListNotesUseCaseImpl(
                    noteRepository = get()
            )
        }
        factory<GetPastListNotesUseCase> {
            GetPastListNotesUseCaseImpl(
                    noteRepository = get()
            )
        }
        factory<MountNoteItemsUseCase> {
            MountNoteItemsUseCaseImpl(
            )
        }
        factory<GetDarkModePreferencesUseCase> {
            GetDarkModePreferencesUseCaseImpl(
                    darkModePreferencesRepository = get()
            )
        }
        factory<ChangeDarkModePreferencesUseCase> {
            ChangeDarkModePreferencesUseCaseImpl(
                    darkModePreferencesRepository = get()
            )
        }
    }
    private val presentationModule = module {
        factory<ListNotesNavigation> { ListNotesNavigationImpl() }
        viewModel {
            ListNotesViewModel(
                    getRecentListNotesUseCase = get(),
                    getPastListNotesUseCase = get(),
                    mountNoteItemsUseCase = get(),
                    getDarkModePreferencesUseCase = get(),
                    changeDarkModePreferencesUseCase = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(dataModule, domainModule, presentationModule))
}