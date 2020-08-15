package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.domain.usecase.impl.GetListNotesUseCase
import me.fabiooliveira.getnotes.domain.usecase.impl.MountNoteItemsUseCaseImpl
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.presentation.navigation.ListNotesNavigationImpl
import me.fabiooliveira.getnotes.presentation.viewmodel.ListNotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ListNotesModule {
    private val domainModule = module {
        factory {
            GetListNotesUseCase(
                    notesRepository = get()
            )
        }
        factory<MountNoteItemsUseCase> {
            MountNoteItemsUseCaseImpl(
            )
        }
    }
    private val presentationModule = module {
        factory<ListNotesNavigation> { ListNotesNavigationImpl() }
        viewModel {
            ListNotesViewModel(
                    getListNotesUseCase = get(),
                    mountNoteItemsUseCase = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(domainModule, presentationModule))
}