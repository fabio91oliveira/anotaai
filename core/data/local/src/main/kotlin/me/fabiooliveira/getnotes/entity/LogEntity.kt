package me.fabiooliveira.getnotes.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "log")
data class LogEntity(val firstLogin: Date) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}