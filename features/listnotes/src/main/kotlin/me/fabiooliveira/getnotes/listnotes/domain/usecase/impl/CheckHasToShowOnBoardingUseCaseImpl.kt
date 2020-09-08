package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.domain.repository.OnBoardingPreferencesRepository
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckHasToShowOnBoardingUseCase

internal class CheckHasToShowOnBoardingUseCaseImpl(
        private val onBoardingPreferencesRepository: OnBoardingPreferencesRepository
) : CheckHasToShowOnBoardingUseCase {
    override suspend operator fun invoke(): Flow<Boolean> {
        return onBoardingPreferencesRepository.hasToShowOnBoarding()
    }
}