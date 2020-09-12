package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.usecase.ChangeDarkModePreferencesUseCase

internal class ChangeDarkModePreferencesUseCaseImpl(
        private val darkModePreferencesRepository: DarkModePreferencesRepository
) : ChangeDarkModePreferencesUseCase {
    override suspend operator fun invoke(isDarkModeEnabled: Boolean): Flow<Unit> {
        return darkModePreferencesRepository.switchDarkMode(isDarkModeEnabled)
    }
}