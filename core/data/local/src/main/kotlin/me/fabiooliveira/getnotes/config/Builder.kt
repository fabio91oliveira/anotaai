package me.fabiooliveira.getnotes.config

import android.content.Context
import androidx.room.Room

internal const val databaseName = "AnotaAi.db"

internal fun provideBuilder(context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        databaseName
)
        .build()