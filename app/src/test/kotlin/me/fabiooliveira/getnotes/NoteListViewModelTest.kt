package me.fabiooliveira.getnotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import me.fabiooliveira.getnotes.model.entity.Note
import me.fabiooliveira.getnotes.feature.notesList.viewModel.NotesListViewModel
import me.fabiooliveira.getnotes.feature.notesList.viewModel.mapper.NoteMapper
import me.fabiooliveira.getnotes.model.entity.Log
import me.fabiooliveira.getnotes.model.repository.LogRepository
import me.fabiooliveira.getnotes.model.repository.NoteRepository
import me.fabiooliveira.getnotes.util.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class NoteListViewModelTest {

    @Rule @JvmField val testSchedulerRule = RxImmediateSchedulerRule()
    @Rule @JvmField val rule = InstantTaskExecutorRule()
    @Rule @JvmField val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var noteRepository: NoteRepository
    @Mock lateinit var logRepository: LogRepository
    @Mock lateinit var noteMapper: NoteMapper

    private lateinit var viewModel: NotesListViewModel

    @Before
    fun setUp() {
        viewModel = NotesListViewModel(noteRepository, logRepository, noteMapper)
    }

    @Test
    fun should_execute_refreshNoteList_success(){
        val note = Note("", "", Date(), 1)
        val noteList = listOf(note)

        Mockito.`when`(noteRepository.getNotes()).thenReturn(Flowable.just(noteList))

        viewModel.refreshNoteList()
        Mockito.verify(noteRepository).getNotes()
    }

    @Test
    fun should_execute_refreshNoteList_error(){
        Mockito.`when`(noteRepository.getNotes()).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.refreshNoteList()
        Mockito.verify(noteRepository).getNotes()
    }

    @Test
    fun should_execute_checkFirstLogin_not_empty_success(){
        val log = Log(Date())
        val logList = listOf(log)

        Mockito.`when`(logRepository.findFirst()).thenReturn(Flowable.just(logList))

        viewModel.checkFirstLogin()
        Mockito.verify(logRepository).findFirst()
    }

    @Test
    fun should_execute_checkFirstLogin_not_empty_error(){
        Mockito.`when`(logRepository.findFirst()).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.checkFirstLogin()
        Mockito.verify(logRepository).findFirst()
    }

    @Test
    fun should_execute_checkFirstLogin_empty_insert_log_success(){
        val logList = mutableListOf<Log>()

        Mockito.`when`(logRepository.findFirst()).thenReturn(Flowable.just(logList))
        Mockito.`when`(logRepository.insertLog(anyObject())).thenReturn(Flowable.just(1))

        viewModel.checkFirstLogin()
        Mockito.verify(logRepository).findFirst()
        Mockito.verify(logRepository).insertLog(anyObject())
    }

    @Test
    fun should_execute_checkFirstLogin_empty_insert_log_error(){
        val logList = mutableListOf<Log>()

        Mockito.`when`(logRepository.findFirst()).thenReturn(Flowable.just(logList))
        Mockito.`when`(logRepository.insertLog(anyObject())).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.checkFirstLogin()
        Mockito.verify(logRepository).findFirst()
        Mockito.verify(logRepository).insertLog(anyObject())
    }

    @Test
    fun should_execute_getNotesTitles(){
        var linkedHashMap = LinkedHashMap<String, MutableList<Note>>()
        linkedHashMap["key"] = mutableListOf()

        viewModel.getNotesTitles(linkedHashMap)
    }

    @Test
    fun should_execute_changeStatus_success(){
        val note = Note("", "", Date(), 1)

        Mockito.`when`(noteRepository.markAsDone(note)).thenReturn(Flowable.just(1))

        viewModel.changeStatus(note)
        Mockito.verify(noteRepository).markAsDone(note)
    }

    @Test
    fun should_execute_markAsDone_error(){
        val note = Note("", "", Date(), 1)

        Mockito.`when`(noteRepository.markAsDone(note)).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.changeStatus(note)
        Mockito.verify(noteRepository).markAsDone(note)
    }

    @Test
    fun should_execute_deleteNote_success(){
        val note = Note("", "", Date(), 1)

        Mockito.`when`(noteRepository.deleteNote(note)).thenReturn(Flowable.just(1))

        viewModel.deleteNote(note)
        Mockito.verify(noteRepository).deleteNote(note)
    }

    @Test
    fun should_execute_deleteNote_error(){
        val note = Note("", "", Date(), 1)

        Mockito.`when`(noteRepository.deleteNote(note)).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.deleteNote(note)
        Mockito.verify(noteRepository).deleteNote(note)
    }

    private fun <T> anyObject(): T {
        return Mockito.any()
    }
}