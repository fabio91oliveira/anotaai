package me.fabiooliveira.getnotes.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.fabiooliveira.getnotes.persistence.dao.LogDao
import me.fabiooliveira.getnotes.persistence.dao.NoteDao
import me.fabiooliveira.getnotes.persistence.source.Database
import javax.inject.Singleton

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Module
class RoomModule(application: Application) {

    private val dataBase: Database = Room.databaseBuilder(
            application,
            Database::class.java,
            "AnotaAi.db"
    ).build()

    @Provides
    @Singleton
    fun provideNoteDatabase(): Database {
        return dataBase
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: Database): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideLogDao(database: Database): LogDao {
        return database.logDao()
    }
}