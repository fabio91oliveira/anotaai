package me.fabiooliveira.anotaai.feature.notesList.viewModel.mapper

import me.fabiooliveira.anotaai.model.entity.Note
import me.fabiooliveira.anotaai.util.DateUtil

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class NoteMapper {
    fun transform(noteList: List<Note>): LinkedHashMap<String, MutableList<Note>> {
        val expandableNoteListDetail = linkedMapOf<String, MutableList<Note>>()
        val noteListAux = noteList.sortedWith(kotlin.Comparator { n1, n2 ->
            if(DateUtil.getMonthFromDate(n1.date) == DateUtil.getMonthFromDate(n2.date)){
                DateUtil.getDayFromDate(n1.date) - DateUtil.getDayFromDate(n2.date)
            } else {
                DateUtil.getMonthFromDate(n1.date) - DateUtil.getMonthFromDate(n2.date)
            }
        })
        noteListAux.forEach {
            if(expandableNoteListDetail[DateUtil.convertDate(it)] == null) {
                expandableNoteListDetail[DateUtil.convertDate(it)] = mutableListOf()
            }
            expandableNoteListDetail[DateUtil.convertDate(it)]?.add(it)
        }
        return expandableNoteListDetail
    }
}