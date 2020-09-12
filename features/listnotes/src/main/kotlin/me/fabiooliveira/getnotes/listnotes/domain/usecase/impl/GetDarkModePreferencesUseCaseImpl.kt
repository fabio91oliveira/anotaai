package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.domain.repository.DarkModePreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetDarkModePreferencesUseCase

internal class GetDarkModePreferencesUseCaseImpl(
        private val darkModePreferencesRepository: DarkModePreferencesRepository
) : GetDarkModePreferencesUseCase {
    override suspend operator fun invoke(): Flow<Boolean> {
        return darkModePreferencesRepository.isDarkMode()
    }
}