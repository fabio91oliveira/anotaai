package me.fabiooliveira.getnotes.listnotes.domain.repository

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.domain.model.UpdateConfig

internal interface UpdateConfigRepository {
    fun getUpdateConfig(): Flow<UpdateConfig>
}