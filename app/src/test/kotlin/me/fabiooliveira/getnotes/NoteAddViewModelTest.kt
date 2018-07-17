package me.fabiooliveira.getnotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import me.fabiooliveira.getnotes.feature.noteAdd.viewModel.NoteAddViewModel
import me.fabiooliveira.getnotes.model.entity.Note
import me.fabiooliveira.getnotes.model.repository.NoteRepository
import me.fabiooliveira.getnotes.util.RxImmediateSchedulerRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class NoteAddViewModelTest {

    @Rule @JvmField val testSchedulerRule = RxImmediateSchedulerRule()
    @Rule @JvmField val rule = InstantTaskExecutorRule()
    @Rule @JvmField val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var repository: NoteRepository

    private lateinit var viewModel: NoteAddViewModel

    @Before
    fun setUp() {
        viewModel = NoteAddViewModel(repository)
    }

    @Test
    fun should_execute_addNote_success(){
        val note = Note("", "", Date(), 1)
        Mockito.`when`(repository.insertNote(note)).thenReturn(Flowable.just(1))

        viewModel.addNote(note)
        Mockito.verify(repository).insertNote(note)
    }

    @Test
    fun should_execute_addNote_error(){
        val note = Note("", "", Date(), 1)
        Mockito.`when`(repository.insertNote(note)).thenReturn(Flowable.error(Throwable("Error")))

        viewModel.addNote(note)
        Mockito.verify(repository).insertNote(note)
    }

    @Test
    fun should_execute_isEmpty_and_get_true() {
        val response = viewModel.isEmpty("")

        Assert.assertEquals(response, true)
    }

    @Test
    fun should_execute_isEmpty_and_get_false() {
        val response = viewModel.isEmpty("test")

        Assert.assertEquals(response, false)
    }

    @Test
    fun should_execute_isDateBeforeToday_and_get_true() {
        val calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR)+1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        val response = !viewModel.isDateBeforeToday(calendar.time)

        Assert.assertEquals(response, true)
    }

    @Test
    fun should_execute_isDateBeforeToday_and_get_false() {
        val calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR)+1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        val response = viewModel.isDateBeforeToday(calendar.time)

        Assert.assertEquals(response, false)
    }

    @Test
    fun should_execute_onDestroy() {
        viewModel.onDestroy()
    }
}