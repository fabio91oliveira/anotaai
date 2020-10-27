package me.fabiooliveira.getnotes.listnotes.domain.usecase

import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig

internal interface CheckIfVersionIsOutDated {
    suspend operator fun invoke(updateConfig: UpdateConfig): Pair<Boolean, UpdateConfig>
}