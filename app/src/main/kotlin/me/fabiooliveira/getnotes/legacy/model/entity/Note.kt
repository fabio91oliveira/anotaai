//package me.fabiooliveira.getnotes.legacy.model.entity
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import java.io.Serializable
//import java.util.Date
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//@Entity(tableName = "note")
//data class Note(var title: String,
//                var contentDescription: String,
//                var date: Date,
//                var relevance: Int): Serializable {
//                @PrimaryKey(autoGenerate = true) var id: Long = 0
//                var isDone: Boolean = false
//}