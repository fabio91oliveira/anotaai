package me.fabiooliveira.getnotes.domain.usecase

internal interface ChangeDarkModePreferencesUseCase {
    suspend operator fun invoke(isDarkModeEnabled: Boolean)
}