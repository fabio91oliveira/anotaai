package me.fabiooliveira.getnotes.data.datasource.local

internal interface DarkModePreferencesLocalDataSource {
    fun isDarkMode(): Boolean
    suspend fun switchDarkMode(darkModeEnabled: Boolean)
}