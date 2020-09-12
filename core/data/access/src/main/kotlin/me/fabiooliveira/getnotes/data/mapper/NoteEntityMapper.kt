package me.fabiooliveira.getnotes.data.mapper

import me.fabiooliveira.getnotes.base.Mapper
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NoteEntityMapper : Mapper<Note, NoteEntity> {
    override fun map(source: Note): NoteEntity {
        return NoteEntity(
                title = source.title,
                contentDescription = source.description,
                date = source.date,
                relevance = source.relevance
        ).apply {
            id = source.id
        }
    }
}