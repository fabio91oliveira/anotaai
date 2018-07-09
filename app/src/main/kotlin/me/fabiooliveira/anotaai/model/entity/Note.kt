package me.fabiooliveira.anotaai.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Entity(tableName = "note")
data class Note(val title: String,
                val contentDescription: String,
                val date: Date,
                val relevance: Int) {
                @PrimaryKey(autoGenerate = true) var id: Long = 0
                var isDone: Boolean = false
}