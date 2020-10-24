package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig
import me.fabiooliveira.getnotes.listnotes.domain.repository.UpdateConfigRepository
import me.fabiooliveira.getnotes.listnotes.domain.usecase.FetchUpdateConfigUseCase

internal class FetchUpdateConfigUseCaseImpl(
        private val updateConfigRepository: UpdateConfigRepository
) : FetchUpdateConfigUseCase {
    override suspend fun invoke(): Flow<UpdateConfig> {
        return updateConfigRepository.getUpdateConfig()
    }
}