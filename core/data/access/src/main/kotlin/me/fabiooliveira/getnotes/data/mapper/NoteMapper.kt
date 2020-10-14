package me.fabiooliveira.getnotes.data.mapper

import me.fabiooliveira.getnotes.base.Mapper
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NoteMapper : Mapper<NoteEntity, Note> {
    override fun map(source: NoteEntity): Note {
        return Note(
                id = source.id,
                title = source.title,
                description = source.contentDescription,
                date = source.date,
                relevance = source.relevance,
                isReminder = source.isReminder
        )
    }
}