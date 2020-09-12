package me.fabiooliveira.getnotes.listnotes.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface ChangeDarkModePreferencesUseCase {
    suspend operator fun invoke(isDarkModeEnabled: Boolean): Flow<Unit>
}