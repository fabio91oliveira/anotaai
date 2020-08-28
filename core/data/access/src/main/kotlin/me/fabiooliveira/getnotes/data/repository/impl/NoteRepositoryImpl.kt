package me.fabiooliveira.getnotes.data.repository.impl

import me.fabiooliveira.getnotes.base.Mapper
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.datasource.NoteLocalDataSource
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NoteRepositoryImpl(
        private val localDataSource: NoteLocalDataSource,
        private val notesPageMapper: Mapper<NoteEntity, Note>,
        private val noteEntityMapper: Mapper<Note, NoteEntity>
) : NoteRepository {
    override suspend fun publishNote(note: Note) {
        localDataSource.insertNote(noteEntityMapper.map(note))
    }

    override suspend fun remoteNote(id: Long) {
        localDataSource.remoteNote(id)
    }

    override suspend fun getNotesFromTodayToFuture(): List<Note> {
        return localDataSource.getNotesFromTodayToFuture().map(notesPageMapper::map)
    }

    override suspend fun getNotesBefore(): List<Note> {
        return localDataSource.getNotesBeforeToday().map(notesPageMapper::map)
    }

}