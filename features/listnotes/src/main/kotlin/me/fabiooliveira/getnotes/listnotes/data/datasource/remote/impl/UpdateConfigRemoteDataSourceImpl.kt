package me.fabiooliveira.getnotes.listnotes.data.datasource.remote.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.listnotes.data.datasource.remote.UpdateConfigRemoteDataSource
import me.fabiooliveira.getnotes.listnotes.data.model.UpdateConfigResponse
import me.fabiooliveira.getnotes.remoteconfig.domain.RemoteConfigManager

private const val UPDATE_CONFIG = "update_config"

internal class UpdateConfigRemoteDataSourceImpl(
        private val remoteConfigManager: RemoteConfigManager
) : UpdateConfigRemoteDataSource {
    override fun fetchUpdateConfig(): Flow<UpdateConfigResponse?> {
        return flow {
            emit(remoteConfigManager.getDynamicObject(UPDATE_CONFIG, UpdateConfigResponse::class.java))
        }
    }
}