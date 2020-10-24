package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.listnotes.data.datasource.local.DarkModePreferencesLocalDataSource
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.OnBoardingPreferencesLocalDataSource
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.impl.DarkModePreferencesLocalDataSourceImpl
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.impl.OnBoardingPreferencesLocalDataSourceImpl
import me.fabiooliveira.getnotes.listnotes.data.datasource.remote.UpdateConfigRemoteDataSource
import me.fabiooliveira.getnotes.listnotes.data.datasource.remote.impl.UpdateConfigRemoteDataSourceImpl
import me.fabiooliveira.getnotes.listnotes.data.model.UpdateConfigResponse
import me.fabiooliveira.getnotes.listnotes.data.repository.DarkModePreferencesRepositoryImpl
import me.fabiooliveira.getnotes.listnotes.data.repository.OnBoardingPreferencesRepositoryImpl
import me.fabiooliveira.getnotes.listnotes.data.repository.UpdateConfigRepositoryImpl
import me.fabiooliveira.getnotes.listnotes.domain.analytics.ListNotesAnalytics
import me.fabiooliveira.getnotes.listnotes.domain.analytics.impl.ListNotesAnalyticsImpl
import me.fabiooliveira.getnotes.listnotes.domain.mapper.UpdateConfigMapper
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig
import me.fabiooliveira.getnotes.listnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.repository.OnBoardingPreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.repository.UpdateConfigRepository
import me.fabiooliveira.getnotes.listnotes.domain.usecase.ChangeDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckHasToShowOnBoardingUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckIfVersionIsOutDated
import me.fabiooliveira.getnotes.listnotes.domain.usecase.FetchUpdateConfigUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetPastListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetRecentListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.ChangeDarkModePreferencesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.CheckHasToShowOnBoardingUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.CheckIfVersionIsOutDatedImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.FetchUpdateConfigUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.GetDarkModePreferencesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.GetPastListNotesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.GetRecentListNotesUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.domain.usecase.impl.MountNoteItemsUseCaseImpl
import me.fabiooliveira.getnotes.listnotes.presentation.navigation.ListNotesNavigationImpl
import me.fabiooliveira.getnotes.listnotes.presentation.viewmodel.ListNotesViewModel
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.onboarding.domain.analytics.OnBoardingAnalytics
import me.fabiooliveira.getnotes.onboarding.domain.analytics.impl.OnBoardingAnalyticsImpl
import me.fabiooliveira.getnotes.onboarding.domain.usecase.CreateOnBoardingScreensUseCase
import me.fabiooliveira.getnotes.onboarding.domain.usecase.impl.CreateOnBoardingScreensUseCaseImpl
import me.fabiooliveira.getnotes.onboarding.presentation.viewmodel.OnBoardingViewModel
import me.fabiooliveira.getnotes.remoteconfig.data.mapper.Mapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

object ListNotesModule {
    private val dataModule = module {
        factory<UpdateConfigRemoteDataSource> {
            UpdateConfigRemoteDataSourceImpl(
                    remoteConfigManager = get()
            )
        }
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
        factory<Mapper<UpdateConfigResponse?, UpdateConfig>>(qualifier = named(UpdateConfigMapper::class.java.name)) {
            UpdateConfigMapper()
        }
        factory<UpdateConfigRepository> {
            UpdateConfigRepositoryImpl(
                    updateConfigRemoteDataSource = get(),
                    updateConfigMapper = get(named(UpdateConfigMapper::class.java.name))
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
        factory<OnBoardingAnalytics> {
            OnBoardingAnalyticsImpl(
                    analytics = get()
            )
        }
        factory<FetchUpdateConfigUseCase> {
            FetchUpdateConfigUseCaseImpl(
                    updateConfigRepository = get()
            )
        }
        factory<CheckIfVersionIsOutDated> {
            CheckIfVersionIsOutDatedImpl(

            )
        }
        factory<ListNotesAnalytics> {
            ListNotesAnalyticsImpl(
                    analytics = get()
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
                    changeDarkModePreferencesUseCase = get(),
                    checkHasToShowOnBoardingUseCase = get(),
                    fetchUpdateConfigUseCase = get(),
                    checkIfVersionIsOutDated = get(),
                    listNotesAnalytics = get()
            )
        }
        viewModel {
            OnBoardingViewModel(
                    createOnBoardingScreensUseCase = get(),
                    onBoardingAnalytics = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(dataModule, domainModule, presentationModule))
}