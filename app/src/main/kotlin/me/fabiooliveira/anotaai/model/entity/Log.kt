package me.fabiooliveira.anotaai.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "log")
data class Log(val firstLogin: Date) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}