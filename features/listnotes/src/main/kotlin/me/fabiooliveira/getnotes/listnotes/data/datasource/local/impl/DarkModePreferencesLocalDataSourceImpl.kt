package me.fabiooliveira.getnotes.listnotes.data.datasource.local.impl

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.DarkModePreferencesLocalDataSource

private const val DARK_MODE_SHARED_PREFERENCES = "dark_mode_shared_preferences"
private const val DARK_MODE_SHARED_PREFERENCES_DEFAULT_VALUE = false

internal class DarkModePreferencesLocalDataSourceImpl(
        private val sharePreferences: SharedPreferences,
        private val sharePreferencesEditor: SharedPreferences.Editor
) : DarkModePreferencesLocalDataSource {
    override fun isDarkMode(): Flow<Boolean> {
        return flow {
            emit(
                    try {
                        sharePreferences.getBoolean(DARK_MODE_SHARED_PREFERENCES, DARK_MODE_SHARED_PREFERENCES_DEFAULT_VALUE)
                    } catch (e: Exception) {
                        DARK_MODE_SHARED_PREFERENCES_DEFAULT_VALUE
                    }
            )
        }
    }

    override fun switchDarkMode(darkModeEnabled: Boolean): Flow<Unit> {
        return flow {
            emit(
                    try {
                        sharePreferencesEditor
                                .putBoolean(DARK_MODE_SHARED_PREFERENCES, darkModeEnabled)
                                .apply()
                    } catch (e: Exception) {

                    }
            )
        }
    }
}