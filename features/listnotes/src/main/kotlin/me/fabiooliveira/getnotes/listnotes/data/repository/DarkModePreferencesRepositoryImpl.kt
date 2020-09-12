package me.fabiooliveira.getnotes.listnotes.data.repository

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.DarkModePreferencesLocalDataSource
import me.fabiooliveira.getnotes.listnotes.domain.repository.DarkModePreferencesRepository

internal class DarkModePreferencesRepositoryImpl(
        private val darkModePreferencesLocalDataSource: DarkModePreferencesLocalDataSource
) : DarkModePreferencesRepository {
    override fun isDarkMode(): Flow<Boolean> {
        return darkModePreferencesLocalDataSource.isDarkMode()
    }

    override fun switchDarkMode(darkModeEnabled: Boolean): Flow<Unit> {
        return darkModePreferencesLocalDataSource.switchDarkMode(darkModeEnabled)
    }
}