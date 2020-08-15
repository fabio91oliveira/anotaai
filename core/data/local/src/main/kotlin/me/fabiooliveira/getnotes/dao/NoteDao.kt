package me.fabiooliveira.getnotes.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
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

    @Update
    fun markAsDone(noteEntity: NoteEntity): Int

    @Delete
    fun delete(noteEntity: NoteEntity): Int

    @Query("SELECT * FROM note")
    fun findAll(): List<NoteEntity>
}