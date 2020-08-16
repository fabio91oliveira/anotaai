package me.fabiooliveira.getnotes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import features.listnotes.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_notes_feature_list_item_note.*
import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal class NoteItemsAdapter : RecyclerView.Adapter<NoteItemsAdapter.NoteViewHolder>() {

    private var notes: MutableList<NoteItem> = mutableListOf()

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_notes_feature_list_item_note,
                        parent,
                        false
                )
        )
    }

    fun addNotes(noteItemsList: List<NoteItem>) {
        notes.addAll(noteItemsList)
    }

    class NoteViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(noteItem: NoteItem) {
            tvNoteTitle.text = noteItem.title
            tvNoteShortDescription.text = noteItem.description
            tvNoteDate.text = noteItem.dateWithHour
            tvNoteNameOfDay.text = if (noteItem.isToday) containerView.resources.getString(R.string.list_notes_feature_note_today) else noteItem.dateName
            tvRelevance.text = containerView.resources.getString(noteItem.relevance.titleRes)
            tvRelevance.setTextColor(ContextCompat.getColor(containerView.context, noteItem.relevance.textColorRes))
            vRelevance.background.setTint(ContextCompat.getColor(containerView.context, noteItem.relevance.backgroundColorRes))
        }
    }
}