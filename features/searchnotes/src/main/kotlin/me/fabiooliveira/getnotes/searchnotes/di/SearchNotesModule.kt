package me.fabiooliveira.getnotes.searchnotes.di

import me.fabiooliveira.getnotes.navigation.SearchNotesNavigation
import me.fabiooliveira.getnotes.searchnotes.domain.analytics.SearchNotesAnalytics
import me.fabiooliveira.getnotes.searchnotes.domain.analytics.impl.SearchNotesAnalyticsImpl
import me.fabiooliveira.getnotes.searchnotes.domain.usecase.GetNotesByTextUseCase
import me.fabiooliveira.getnotes.searchnotes.domain.usecase.impl.GetNotesByTextUseCaseImpl
import me.fabiooliveira.getnotes.searchnotes.presentation.navigation.SearchNotesNavigationImpl
import me.fabiooliveira.getnotes.searchnotes.presentation.viewmodel.SearchNotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object SearchNotesModule {
    private val domainModule = module {
        factory<GetNotesByTextUseCase> {
            GetNotesByTextUseCaseImpl(
                    noteRepository = get()
            )
        }
        factory<SearchNotesAnalytics> {
            SearchNotesAnalyticsImpl(
                    analytics = get()
            )
        }
    }
    private val presentationModule = module {
        factory<SearchNotesNavigation> { SearchNotesNavigationImpl() }
        viewModel {
            SearchNotesViewModel(
                    getNotesByTextUseCase = get(),
                    mountNoteItemsUseCase = get(),
                    searchNotesAnalytics = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(domainModule, presentationModule))
}