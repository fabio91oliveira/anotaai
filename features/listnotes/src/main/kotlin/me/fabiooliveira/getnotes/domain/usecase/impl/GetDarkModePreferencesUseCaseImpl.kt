package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.domain.usecase.GetDarkModePreferencesUseCase

internal class GetDarkModePreferencesUseCaseImpl(
        private val darkModePreferencesRepository: DarkModePreferencesRepository
) : GetDarkModePreferencesUseCase {
    override suspend operator fun invoke() = withContext(Dispatchers.Main) {
        darkModePreferencesRepository.isDarkMode()
    }
}