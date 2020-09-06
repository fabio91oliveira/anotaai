package me.fabiooliveira.getnotes.domain.usecase

internal interface GetDarkModePreferencesUseCase {
    suspend operator fun invoke(): Boolean
}