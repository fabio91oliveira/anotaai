//package me.fabiooliveira.getnotes.legacy.model.repository
//
//import io.reactivex.Flowable
//import me.fabiooliveira.getnotes.legacy.model.entity.Note
//import me.fabiooliveira.getnotes.legacy.persistence.dao.NoteDao
//import javax.inject.Inject
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
//
//    fun insertNote(note: Note): Flowable<Long> {
//        return Flowable.fromCallable {noteDao.insert(note)}
//    }
//
//    fun markAsDone(note: Note): Flowable<Long> {
//        return Flowable.fromCallable {noteDao.markAsDone(note).toLong()}
//    }
//
//    fun deleteNote(note: Note): Flowable<Long> {
//        return Flowable.fromCallable {noteDao.delete(note).toLong()}
//    }
//
//    fun getNotes(): Flowable<List<Note>> {
//        return noteDao.findAll()
//    }
//}