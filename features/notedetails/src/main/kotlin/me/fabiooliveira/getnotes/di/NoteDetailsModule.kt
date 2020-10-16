package me.fabiooliveira.getnotes.di

import me.fabiooliveira.getnotes.domain.usecase.PublishNoteUseCase
import me.fabiooliveira.getnotes.domain.usecase.RemoveNoteUseCase
import me.fabiooliveira.getnotes.domain.usecase.ValidateFieldsUseCase
import me.fabiooliveira.getnotes.domain.usecase.impl.PublishNoteUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.RemoveNoteUseCaseImpl
import me.fabiooliveira.getnotes.domain.usecase.impl.ValidateFieldsUseCaseImpl
import me.fabiooliveira.getnotes.navigation.NoteDetailsNavigation
import me.fabiooliveira.getnotes.presentation.navigation.NoteDetailsDetailsNavigationImpl
import me.fabiooliveira.getnotes.presentation.viewmodel.NoteDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.util.*

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
        factory<ValidateFieldsUseCase> { ValidateFieldsUseCaseImpl() }
    }

    private val presentationModule = module {

        viewModel { (calendar: Calendar?) ->
            NoteDetailsViewModel(
                    calendar = calendar ?: Calendar.getInstance(),
                    publishNoteUseCase = get(),
                    removeNoteUseCase = get(),
                    validateFieldsUseCase = get()
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