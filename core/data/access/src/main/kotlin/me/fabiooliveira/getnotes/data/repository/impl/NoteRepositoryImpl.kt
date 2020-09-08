package me.fabiooliveira.getnotes.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    override fun publishNote(note: Note): Flow<Long> {
        return localDataSource.insertNote(noteEntityMapper.map(note))
    }

    override fun remoteNote(id: Long): Flow<Int> {
        return localDataSource.remoteNote(id)
    }

    override fun getNotesFromTodayToFuture(): Flow<List<Note>> {
        return localDataSource.getNotesFromTodayToFuture().map {
            it.map(notesPageMapper::map)
        }
    }

    override fun getNotesBefore(): Flow<List<Note>> {
        return localDataSource.getNotesBeforeToday().map {
            it.map(notesPageMapper::map)
        }
    }

}