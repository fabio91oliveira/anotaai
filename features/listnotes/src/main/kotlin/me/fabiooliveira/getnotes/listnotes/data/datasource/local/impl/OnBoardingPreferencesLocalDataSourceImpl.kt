package me.fabiooliveira.getnotes.listnotes.data.datasource.local.impl

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.OnBoardingPreferencesLocalDataSource

private const val ON_BOARDING_SHARED_PREFERENCES = "on_boarding_shared_preferences"
private const val ON_BOARDING_SHARED_PREFERENCES_DEFAULT_VALUE = true
private const val ON_BOARDING_SHARED_PREFERENCES_FALSE_VALUE = false

internal class OnBoardingPreferencesLocalDataSourceImpl(
        private val sharePreferences: SharedPreferences,
        private val sharePreferencesEditor: SharedPreferences.Editor
) : OnBoardingPreferencesLocalDataSource {
    override fun hasToShowOnBoarding(): Flow<Boolean> {
        return flow {
            emit(
                    try {
                        val hasToShow = sharePreferences.getBoolean(ON_BOARDING_SHARED_PREFERENCES, ON_BOARDING_SHARED_PREFERENCES_DEFAULT_VALUE)

                        if (hasToShow) {
                            sharePreferencesEditor
                                    .putBoolean(ON_BOARDING_SHARED_PREFERENCES, ON_BOARDING_SHARED_PREFERENCES_FALSE_VALUE)
                                    .apply()

                            hasToShow
                        } else {
                            ON_BOARDING_SHARED_PREFERENCES_FALSE_VALUE
                        }
                    } catch (e: Exception) {
                        ON_BOARDING_SHARED_PREFERENCES_FALSE_VALUE
                    }
            )
        }
    }
}