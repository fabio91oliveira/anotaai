package me.fabiooliveira.getnotes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import me.fabiooliveira.getnotes.entity.LogEntity

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Dao
interface LogDao {
    @Insert(onConflict = REPLACE)
    fun insert(logEntity: LogEntity): Long

    @Query("SELECT * FROM log")
    fun findAll(): List<LogEntity>
}