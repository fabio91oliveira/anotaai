package me.fabiooliveira.getnotes.listnotes.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface GetDarkModePreferencesUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}