package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import features.listnotes.BuildConfig
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckIfVersionIsOutDated

internal class CheckIfVersionIsOutDatedImpl : CheckIfVersionIsOutDated {
    override suspend operator fun invoke(updateConfig: UpdateConfig): Pair<Boolean, UpdateConfig> {
        return Pair(updateConfig.latestVersionCode > BuildConfig.VERSION_CODE, updateConfig)
    }
}