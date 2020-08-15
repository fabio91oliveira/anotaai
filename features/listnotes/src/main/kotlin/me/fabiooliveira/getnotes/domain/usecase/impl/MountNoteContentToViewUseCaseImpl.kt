package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.domain.usecase.MountNoteContentToViewUseCase
import me.fabiooliveira.getnotes.extensions.getDayNumber
import me.fabiooliveira.getnotes.presentation.vo.AdapterItem
import me.fabiooliveira.getnotes.presentation.vo.NoteContent
import java.text.SimpleDateFormat
import java.util.*

internal class MountNoteContentToViewUseCaseImpl : MountNoteContentToViewUseCase {
    override suspend fun invoke(notesList: List<Note>) =
            withContext(Dispatchers.Default) {
                mount(notesList)
            }

    private fun mount(notesList: List<Note>): MutableList<AdapterItem<Pair<String, String>, NoteContent>> {
        return mutableListOf<AdapterItem<Pair<String, String>, NoteContent>>().apply {
            var lastDay = 1
            notesList.forEach {
                if (it.date.getDayNumber() != lastDay) {
                    lastDay = it.date.getDayNumber()
                    add(
                            AdapterItem(
                                    first = Pair(getDayName(it.date), getShortDate(it.date)),
                                    viewType = AdapterItem.ViewType.HEADER
                            )
                    )
                    add(
                            AdapterItem(
                                    second = createNoteContent(it),
                                    viewType = AdapterItem.ViewType.ITEM
                            )
                    )
                } else {
                    add(
                            AdapterItem(
                                    second = createNoteContent(it),
                                    viewType = AdapterItem.ViewType.ITEM
                            )
                    )
                }
            }
        }
    }

    private fun createNoteContent(note: Note) =
            NoteContent(
                    id = note.id,
                    title = note.title,
                    description = note.description,
                    isDone = note.isDone
            )

    private fun getDayName(date: Date) = if (isToday(date)) "Today" else "Sunday"
    private fun getShortDate(date: Date) = date.getDateStringByFormat("MMM dd")

    fun Date.getDateStringByFormat(pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(this).capitalize()
    }

    fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val specifiedDate = Calendar.getInstance()
        specifiedDate.time = date
        return today[Calendar.DAY_OF_MONTH] === specifiedDate[Calendar.DAY_OF_MONTH] && today[Calendar.MONTH] === specifiedDate[Calendar.MONTH] && today[Calendar.YEAR] === specifiedDate[Calendar.YEAR]
    }
}