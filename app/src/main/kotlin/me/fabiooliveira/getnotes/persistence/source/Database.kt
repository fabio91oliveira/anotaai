package me.fabiooliveira.getnotes.persistence.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.fabiooliveira.getnotes.model.entity.Log
import me.fabiooliveira.getnotes.model.entity.Note
import me.fabiooliveira.getnotes.persistence.converter.DateConverter
import me.fabiooliveira.getnotes.persistence.dao.LogDao
import me.fabiooliveira.getnotes.persistence.dao.NoteDao

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Database(entities = [(Note::class), (Log::class)], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun logDao(): LogDao
}