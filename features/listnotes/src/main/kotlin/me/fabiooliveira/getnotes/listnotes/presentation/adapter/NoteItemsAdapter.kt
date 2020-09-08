package me.fabiooliveira.getnotes.listnotes.presentation.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import features.listnotes.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_notes_feature_list_item_note.*
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

private const val DURATION = 300L

internal class NoteItemsAdapter(
        private val noteClickListener: NoteClickListener
) : RecyclerView.Adapter<NoteItemsAdapter.NoteViewHolder>() {

    private var notes: MutableList<NoteItem> = mutableListOf()
    private var onAttach = true
    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
        setAnimation(holder.itemView, position)
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                onAttach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        super.onAttachedToRecyclerView(recyclerView)
    }

    fun addNotes(noteItemsList: List<NoteItem>) {
        notes.addAll(noteItemsList)
    }

    fun clearNotes() {
        notes.clear()
    }

    private fun setAnimation(itemView: View, position: Int) {
        var i = position
        if (!onAttach) {
            i = -1
        }
        val isNotFirstItem = i == -1
        i++
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animator.startDelay = if (isNotFirstItem) DURATION / 2 else i * DURATION / 2
        animator.duration = 250
        animatorSet.play(animator)
        animator.start()
    }

    inner class NoteViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(noteItem: NoteItem) {
            tvNoteTitle.text = noteItem.title
            tvNoteShortDescription.text = noteItem.description
            etNoteDate.text = noteItem.date
            tvNoteNameOfDay.text = if (noteItem.isToday) containerView.resources.getString(R.string.list_notes_feature_note_today) else noteItem.dateName
            tvRelevance.text = containerView.resources.getString(noteItem.relevance.titleRes)
            tvRelevance.setTextColor(ContextCompat.getColor(containerView.context, noteItem.relevance.textColorRes))
            vRelevance.background.setTint(ContextCompat.getColor(containerView.context, noteItem.relevance.backgroundColorRes))
            contentNote.id = ViewCompat.generateViewId()
            contentNote.setOnClickListener {
                noteClickListener.onClickNote(noteItem, it)
            }
        }
    }

    interface NoteClickListener {
        fun onClickNote(noteItem: NoteItem, view: View)
    }
}