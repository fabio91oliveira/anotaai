package me.fabiooliveira.getnotes.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.fabiooliveira.getnotes.dao.LogDao
import me.fabiooliveira.getnotes.dao.NoteDao
import me.fabiooliveira.getnotes.entity.LogEntity
import me.fabiooliveira.getnotes.entity.NoteEntity
import me.fabiooliveira.getnotes.entity.converter.DateConverter

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Database(entities = [(NoteEntity::class), (LogEntity::class)], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun logDao(): LogDao
}