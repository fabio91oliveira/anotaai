package me.fabiooliveira.getnotes.data.repository

import me.fabiooliveira.getnotes.data.datasource.local.DarkModePreferencesLocalDataSource
import me.fabiooliveira.getnotes.domain.repository.DarkModePreferencesRepository

internal class DarkModePreferencesRepositoryImpl(
        private val darkModePreferencesLocalDataSource: DarkModePreferencesLocalDataSource
) : DarkModePreferencesRepository {
    override suspend fun isDarkMode(): Boolean {
        return darkModePreferencesLocalDataSource.isDarkMode()
    }

    override suspend fun switchDarkMode(darkModeEnabled: Boolean) {
        darkModePreferencesLocalDataSource.switchDarkMode(darkModeEnabled)
    }
}