package me.fabiooliveira.getnotes.listnotes.domain.repository

import kotlinx.coroutines.flow.Flow

internal interface OnBoardingPreferencesRepository {
    fun hasToShowOnBoarding(): Flow<Boolean>
}