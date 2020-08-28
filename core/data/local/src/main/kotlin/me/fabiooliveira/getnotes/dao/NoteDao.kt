package me.fabiooliveira.getnotes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
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

    @Query("SELECT * FROM note WHERE strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) >= strftime('%Y %m %d','now','localtime') ORDER BY date ASC, relevance DESC, id DESC")
    fun findNotesStartingFromToday(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE strftime('%Y %m %d', datetime(date/1000, 'unixepoch')) < strftime('%Y %m %d','now','localtime') ORDER BY date ASC, relevance DESC, id DESC")
    fun findNotesBeforeToday(): List<NoteEntity>
}