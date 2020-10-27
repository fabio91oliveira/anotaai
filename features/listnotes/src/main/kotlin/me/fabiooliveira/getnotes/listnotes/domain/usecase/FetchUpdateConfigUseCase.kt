package me.fabiooliveira.getnotes.listnotes.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig

internal interface FetchUpdateConfigUseCase {
    suspend operator fun invoke(): Flow<UpdateConfig>
}