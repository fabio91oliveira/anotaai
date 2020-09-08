package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.listnotes.data.datasource.local.DarkModePreferencesLocalDataSource
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.OnBoardingPreferencesLocalDataSource
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.impl.DarkModePreferencesLocalDataSourceImpl
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.impl.OnBoardingPreferencesLocalDataSourceImpl
import me.fabiooliveira.getnotes.listnotes.data.repository.DarkModePreferencesRepositoryImpl
import me.fabiooliveira.getnotes.listnotes.data.repository.OnBoardingPreferencesRepositoryImpl
import me.fabiooliveira.getnotes.listnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.repository.OnBoardingPreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.usecase.ChangeDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckHasToShowOnBoardingUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetPastListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetRecentListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.ChangeDarkModePreferencesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.CheckHasToShowOnBoardingUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.GetDarkModePreferencesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.GetPastListNotesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.GetRecentListNotesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.MountNoteItemsUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.presentation.navigation.ListNotesNavigationImpl
import me.fabiooliveira.getnotes.listnotes.presentation.viewmodel.ListNotesViewModel
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.onboarding.domain.usecase.CreateOnBoardingScreensUseCase
import me.fabiooliveira.getnotes.onboarding.domain.usecase.impl.CreateOnBoardingScreensUseCaseImpl
import me.fabiooliveira.getnotes.onboarding.presentation.viewmodel.OnBoardingViewModel
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
        factory<OnBoardingPreferencesLocalDataSource> {
            OnBoardingPreferencesLocalDataSourceImpl(
                    sharePreferences = get(),
                    sharePreferencesEditor = get()
            )
        }
        factory<DarkModePreferencesRepository> {
            DarkModePreferencesRepositoryImpl(
                    darkModePreferencesLocalDataSource = get()
            )
        }
        factory<OnBoardingPreferencesRepository> {
            OnBoardingPreferencesRepositoryImpl(
                    onBoardingPreferencesLocalDataSource = get()
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
        factory<CheckHasToShowOnBoardingUseCase> {
            CheckHasToShowOnBoardingUseCaseImpl(
                    onBoardingPreferencesRepository = get()
            )
        }
        factory<CreateOnBoardingScreensUseCase> { CreateOnBoardingScreensUseCaseImpl() }
    }
    private val presentationModule = module {
        factory<ListNotesNavigation> { ListNotesNavigationImpl() }
        viewModel {
            ListNotesViewModel(
                    getRecentListNotesUseCase = get(),
                    getPastListNotesUseCase = get(),
                    mountNoteItemsUseCase = get(),
                    getDarkModePreferencesUseCase = get(),
                    changeDarkModePreferencesUseCase = get(),
                    checkHasToShowOnBoardingUseCase = get()
            )
        }
        viewModel {
            OnBoardingViewModel(
                    createOnBoardingScreensUseCase = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(dataModule, domainModule, presentationModule))
}