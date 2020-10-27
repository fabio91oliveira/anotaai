package me.fabiooliveira.getnotes.listnotes.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.fabiooliveira.getnotes.listnotes.data.datasource.remote.UpdateConfigRemoteDataSource
import me.fabiooliveira.getnotes.listnotes.data.model.UpdateConfigResponse
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig
import me.fabiooliveira.getnotes.listnotes.domain.repository.UpdateConfigRepository
import me.fabiooliveira.getnotes.remoteconfig.data.mapper.Mapper

internal class UpdateConfigRepositoryImpl(
        private val updateConfigRemoteDataSource: UpdateConfigRemoteDataSource,
        private val updateConfigMapper: Mapper<UpdateConfigResponse?, UpdateConfig>
) : UpdateConfigRepository {
    override fun getUpdateConfig(): Flow<UpdateConfig> {
        return updateConfigRemoteDataSource.fetchUpdateConfig().map {
            updateConfigMapper.transform(it)
        }
    }
}