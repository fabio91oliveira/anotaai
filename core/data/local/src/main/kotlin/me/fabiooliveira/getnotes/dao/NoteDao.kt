package me.fabiooliveira.getnotes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.entity.NoteEntity

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE)
    fun insert(noteEntity: NoteEntity): Long

    @Query("DELETE FROM note WHERE id == :id")
    fun delete(id: Long): Int

    @Query("SELECT * FROM note WHERE strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) >= strftime('%Y %m %d','now','localtime') ORDER BY strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) ASC, relevance DESC, id DESC")
    fun findNotesStartingFromToday(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) < strftime('%Y %m %d','now','localtime') ORDER BY strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) ASC, relevance DESC, id DESC")
    fun findNotesBeforeToday(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE title LIKE '%' || :text || '%' OR contentDescription LIKE '%' || :text || '%'")
    fun findNotesByText(text: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) == strftime('%Y %m %d','now','localtime') ORDER BY strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) ASC, relevance DESC, id DESC")
    fun findNotesOnlyFromToday(): List<NoteEntity>

    @Query("UPDATE note SET isReminder = 0 WHERE id = :id")
    fun cancelReminder(id: Long): Int
}