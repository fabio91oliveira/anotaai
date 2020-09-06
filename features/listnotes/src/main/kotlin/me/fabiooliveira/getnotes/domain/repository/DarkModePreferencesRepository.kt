package me.fabiooliveira.getnotes.domain.repository

internal interface DarkModePreferencesRepository {
    suspend fun isDarkMode(): Boolean
    suspend fun switchDarkMode(darkModeEnabled: Boolean)
}