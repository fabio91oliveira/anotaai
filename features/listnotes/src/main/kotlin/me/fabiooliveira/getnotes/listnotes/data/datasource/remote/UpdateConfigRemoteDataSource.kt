package me.fabiooliveira.getnotes.listnotes.data.datasource.remote

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.data.model.UpdateConfigResponse

internal interface UpdateConfigRemoteDataSource {
    fun fetchUpdateConfig(): Flow<UpdateConfigResponse?>
}