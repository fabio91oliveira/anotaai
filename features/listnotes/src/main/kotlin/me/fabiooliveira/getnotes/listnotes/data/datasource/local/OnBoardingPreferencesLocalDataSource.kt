package me.fabiooliveira.getnotes.listnotes.data.datasource.local

import kotlinx.coroutines.flow.Flow

internal interface OnBoardingPreferencesLocalDataSource {
    fun hasToShowOnBoarding(): Flow<Boolean>
}