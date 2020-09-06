package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.domain.usecase.ChangeDarkModePreferencesUseCase

internal class ChangeDarkModePreferencesUseCaseImpl(
        private val darkModePreferencesRepository: DarkModePreferencesRepository
) : ChangeDarkModePreferencesUseCase {
    override suspend operator fun invoke(isDarkModeEnabled: Boolean) = withContext(Dispatchers.Main) {
        darkModePreferencesRepository.switchDarkMode(isDarkModeEnabled)
    }
}