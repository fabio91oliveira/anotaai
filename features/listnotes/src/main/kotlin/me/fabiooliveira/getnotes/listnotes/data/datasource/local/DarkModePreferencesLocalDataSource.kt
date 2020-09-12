package me.fabiooliveira.getnotes.listnotes.data.datasource.local

import kotlinx.coroutines.flow.Flow

internal interface DarkModePreferencesLocalDataSource {
    fun isDarkMode(): Flow<Boolean>
    fun switchDarkMode(darkModeEnabled: Boolean): Flow<Unit>
}