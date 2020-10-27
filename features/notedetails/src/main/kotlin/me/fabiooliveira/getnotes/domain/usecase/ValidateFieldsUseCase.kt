package me.fabiooliveira.getnotes.domain.usecase

import kotlinx.coroutines.flow.Flow
import java.util.*

internal interface ValidateFieldsUseCase {
    suspend operator fun invoke(
            titleNote: String,
            descriptionNote: String,
            date: String,
            time: String,
            isReminder: Boolean,
            calendar: Calendar
    ): Flow<Boolean>
}