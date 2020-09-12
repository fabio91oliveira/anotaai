package me.fabiooliveira.getnotes.listnotes.domain.repository

import kotlinx.coroutines.flow.Flow

internal interface DarkModePreferencesRepository {
    fun isDarkMode(): Flow<Boolean>
    fun switchDarkMode(darkModeEnabled: Boolean): Flow<Unit>
}