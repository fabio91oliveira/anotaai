package me.fabiooliveira.getnotes.persistence.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import me.fabiooliveira.getnotes.model.entity.Log

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Dao
interface LogDao {
    @Insert(onConflict = REPLACE) fun insert(log: Log): Long
    @Query("SELECT * FROM log") fun findAll(): Flowable<List<Log>>
}