package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.domain.usecase.PublishNoteUseCase
import me.fabiooliveira.getnotes.domain.usecase.RemoveNoteUseCase
import me.fabiooliveira.getnotes.domain.usecase.ValidateEmptyFieldsUseCase
import me.fabiooliveira.getnotes.domain.usecase.impl.PublishNoteUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.RemoveNoteUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.ValidateEmptyFieldsUseCaseImpl
import me.fabiooliveira.getnotes.navigation.NoteDetailsNavigation
import me.fabiooliveira.getnotes.presentation.navigation.NoteDetailsDetailsNavigationImpl
import me.fabiooliveira.getnotes.presentation.viewmodel.NoteDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object NoteDetailsModule {
    private val domainModule = module {
        factory<PublishNoteUseCase> {
            PublishNoteUseCaseImpl(
                    noteRepository = get()
            )
        }
        factory<RemoveNoteUseCase> {
            RemoveNoteUseCaseImpl(
                    noteRepository = get()
            )
        }
        factory<ValidateEmptyFieldsUseCase> { ValidateEmptyFieldsUseCaseImpl() }
    }

    private val presentationModule = module {
        viewModel {
            NoteDetailsViewModel(
                    publishNoteUseCase = get(),
                    removeNoteUseCase = get(),
                    validateEmptyFieldsUseCase = get()
            )
        }
    }

    private val navigationModule = module {
        factory<NoteDetailsNavigation> {
            NoteDetailsDetailsNavigationImpl(
            )
        }
    }

    fun load() = loadKoinModules(listOf(
            domainModule,
            presentationModule,
            navigationModule
    ))
}