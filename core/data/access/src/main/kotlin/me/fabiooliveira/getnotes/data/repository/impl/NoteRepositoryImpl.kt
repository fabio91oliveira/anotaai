package me.fabiooliveira.getnotes.data.repository.impl

import me.fabiooliveira.getnotes.base.Mapper
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.datasource.NoteLocalDataSource
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity
import java.util.*

internal class NoteRepositoryImpl(
        private val localDataSource: NoteLocalDataSource,
        private val notesPageMapper: Mapper<NoteEntity, Note>
) : NoteRepository {
    override suspend fun getNotes(): List<Note> {
//        return localDataSource.listNotes().map(notesPageMapper::map)

        val lista = mutableListOf<NoteEntity>().apply {
            val note = NoteEntity(
                    "Lorem Ipsom",
                    "Lorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem Ipsom",
                    Date(),
                    1
            )
            add(note)
            add(note)
            add(note)
            add(note)
            add(note)
            add(note)
            add(note)
            add(note)
            add(note)

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, -2)
            val note2 = NoteEntity(
                    "Lorem Ipsom",
                    "Lorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem IpsomLorem Ipsom",
                    calendar.time,
                    1
            )

            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
            add(note2)
        }

        return lista.toList().map(notesPageMapper::map)
    }
}