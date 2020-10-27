package me.fabiooliveira.getnotes.config

import android.content.Context
import androidx.room.Room
import me.fabiooliveira.getnotes.migration.Migrations.MIGRATION_1_2

internal const val databaseName = "AnotaAi.db"

internal fun provideBuilder(context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        databaseName
)
        .addMigrations(MIGRATION_1_2)
        .build()