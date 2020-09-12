package me.fabiooliveira.getnotes.listnotes.data.repository

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.data.datasource.local.OnBoardingPreferencesLocalDataSource
import me.fabiooliveira.getnotes.listnotes.domain.repository.OnBoardingPreferencesRepository

internal class OnBoardingPreferencesRepositoryImpl(
        private val onBoardingPreferencesLocalDataSource: OnBoardingPreferencesLocalDataSource
) : OnBoardingPreferencesRepository {
    override fun hasToShowOnBoarding(): Flow<Boolean> {
        return onBoardingPreferencesLocalDataSource.hasToShowOnBoarding()
    }
}