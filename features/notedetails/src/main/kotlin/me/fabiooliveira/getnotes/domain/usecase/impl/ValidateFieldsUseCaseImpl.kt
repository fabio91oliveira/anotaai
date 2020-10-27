package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.domain.exception.NoteDetailsException
import me.fabiooliveira.getnotes.domain.usecase.ValidateFieldsUseCase
import java.util.*

internal class ValidateFieldsUseCaseImpl : ValidateFieldsUseCase {
    override suspend fun invoke(
            titleNote: String,
            descriptionNote: String,
            date: String,
            time: String,
            isReminder: Boolean,
            calendar: Calendar
    ): Flow<Boolean> {
        return flow {
            if (isReminder) validateDate(calendar)
            validateFields(titleNote, descriptionNote, date, time)
            emit(true)
        }
    }

    private fun validateDate(calendar: Calendar) {
        val now = Calendar.getInstance()
        if (calendar.before(now) || calendar == now) throw NoteDetailsException.DateAndTimeBeforeThanTodayException
    }

    private fun validateFields(
            titleNote: String,
            descriptionNote: String,
            date: String,
            time: String,
    ) {
        if (titleNote.isEmpty() ||
                descriptionNote.isEmpty() ||
                date.isEmpty() ||
                time.isEmpty()) {
            throw NoteDetailsException.EmptyFieldsException
        }
    }
}