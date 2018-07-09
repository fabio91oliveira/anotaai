package me.fabiooliveira.anotaai.persistence.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import me.fabiooliveira.anotaai.model.entity.Note
import java.util.Date

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE) fun insert(note: Note): Long
    @Update
    fun markAsDone(note: Note): Int
    @Delete fun delete(note: Note): Int
    @Query("SELECT * FROM note") fun findAll(): Flowable<List<Note>>
    @Query("UPDATE note SET isDone = 1 WHERE date < :date and isDone != 1") fun markAllAsDone(date: Date): Int
}