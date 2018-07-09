package me.fabiooliveira.anotaai

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import me.fabiooliveira.anotaai.model.entity.Note
import me.fabiooliveira.anotaai.feature.notesList.viewModel.NotesListViewModel
import me.fabiooliveira.anotaai.feature.notesList.viewModel.mapper.NoteMapper
import me.fabiooliveira.anotaai.model.repository.NoteRepository
import me.fabiooliveira.anotaai.util.RxImmediateSchedulerRule
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
    @Mock lateinit var noteMapper: NoteMapper

    private lateinit var viewModel: NotesListViewModel

    @Before
    fun setUp() {
        viewModel = NotesListViewModel(noteRepository, noteMapper)
    }

    @Test
    fun should_execute_onCreate_success(){
        val noteList = mutableListOf<Note>()
        val note = Note("", "", Date(), 1)
        noteList.add(note)

        val flowable = Flowable.just(noteList.toList())

        Mockito.`when`(noteRepository.markAllAsDone(anyObject())).thenReturn(Flowable.just(11))
        Mockito.`when`(noteRepository.getNotes()).thenReturn(flowable)
        viewModel.onCreate()
        Mockito.verify(noteRepository).markAllAsDone(anyObject())
        Mockito.verify(noteRepository).getNotes()
    }

    @Test
    fun should_execute_onCreate_error(){
        Mockito.`when`(noteRepository.markAllAsDone(anyObject())).thenReturn(Flowable.error(Throwable("Error")))
        viewModel.onCreate()
        Mockito.verify(noteRepository).markAllAsDone(anyObject())
    }

    @Test
    fun should_execute_getNotesTitles(){
        var linkedHashMap = LinkedHashMap<String, MutableList<Note>>()
        linkedHashMap["key"] = mutableListOf()

        viewModel.getNotesTitles(linkedHashMap)
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
    fun should_execute_markAsDone_success(){
        val note = Note("", "", Date(), 1)

        Mockito.`when`(noteRepository.markAsDone(note)).thenReturn(Flowable.just(1))

        viewModel.markAsDone(note)
        Mockito.verify(noteRepository).markAsDone(note)
    }

    @Test
    fun should_execute_markAsDone_error(){
        val note = Note("", "", Date(), 1)

        Mockito.`when`(noteRepository.markAsDone(note)).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.markAsDone(note)
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