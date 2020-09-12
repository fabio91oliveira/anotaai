package me.fabiooliveira.getnotes.listnotes.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface CheckHasToShowOnBoardingUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}