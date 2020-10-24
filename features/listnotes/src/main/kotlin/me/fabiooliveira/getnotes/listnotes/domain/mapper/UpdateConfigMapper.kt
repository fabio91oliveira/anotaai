package me.fabiooliveira.getnotes.listnotes.domain.mapper

import me.fabiooliveira.getnotes.listnotes.data.model.UpdateConfigResponse
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig
import me.fabiooliveira.getnotes.remoteconfig.data.mapper.Mapper

internal class UpdateConfigMapper : Mapper<UpdateConfigResponse?, UpdateConfig> {
    override fun transform(source: UpdateConfigResponse?): UpdateConfig {
        return UpdateConfig(
                latestVersionCode = source?.latestVersionCode ?: 0L,
                forceUpdate = source?.forceUpdate ?: false
        )
    }
}