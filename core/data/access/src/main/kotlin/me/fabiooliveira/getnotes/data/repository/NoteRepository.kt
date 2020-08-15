package me.fabiooliveira.getnotes.data.repository

import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity

interface NoteRepository {
    suspend fun getNotes(): List<Note>
}