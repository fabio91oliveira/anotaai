package me.fabiooliveira.getnotes.onboarding.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.onboarding.presentation.vo.OnBoardingScreen

internal interface CreateOnBoardingScreensUseCase {
    suspend operator fun invoke(): Flow<MutableList<OnBoardingScreen>>
}