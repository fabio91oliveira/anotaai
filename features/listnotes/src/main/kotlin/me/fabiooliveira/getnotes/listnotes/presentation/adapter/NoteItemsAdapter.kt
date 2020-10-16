package me.fabiooliveira.getnotes.listnotes.presentation.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import features.listnotes.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_notes_feature_list_item_note.*
import me.fabiooliveira.getnotes.extensions.getDateString
import me.fabiooliveira.getnotes.extensions.getTimeOfTheDay
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

private const val DURATION = 300L

class NoteItemsAdapter(
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
            etNoteDate.text = noteItem.calendar.time.getDateString()
            tvNoteTime.text = noteItem.calendar.time.getTimeOfTheDay(containerView.context)

            if (noteItem.isToday) {
                tvNoteNameOfDay.text = containerView.resources.getString(R.string.list_notes_feature_note_today)
                tvNoteNameOfDay.setTextColor(ContextCompat.getColor(containerView.context, R.color.color_accent_dark))
            } else {
                tvNoteNameOfDay.text = noteItem.dateName
                tvNoteNameOfDay.setTextColor(ContextCompat.getColor(containerView.context, R.color.color_scale_2))
            }

            if (noteItem.isReminder) {
                AppCompatResources.getDrawable(containerView.context, R.drawable.list_notes_feature_ic_baseline_alarm_24)?.apply {
                    val wrappedDrawable: Drawable = DrawableCompat.wrap(this)
                    DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(containerView.context, R.color.color_accent))
                    ivReminderSet.background = wrappedDrawable
                }
            } else {
                AppCompatResources.getDrawable(containerView.context, R.drawable.list_notes_feature_ic_baseline_alarm_off_24)?.apply {
                    val wrappedDrawable: Drawable = DrawableCompat.wrap(this)
                    DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(containerView.context, R.color.color_scale_4))
                    ivReminderSet.background = wrappedDrawable
                }
            }

            tvNoteNameOfDay.text = if (noteItem.isToday) containerView.resources.getString(R.string.list_notes_feature_note_today) else noteItem.dateName

            tvRelevance.text = containerView.resources.getString(noteItem.relevance.titleRes)
            tvRelevance.setTextColor(ContextCompat.getColor(containerView.context, noteItem.relevance.textColorRes))
            vRelevance.background.setTint(ContextCompat.getColor(containerView.context, noteItem.relevance.backgroundColorRes))
            contentNote.id = ViewCompat.generateViewId()
            contentNote.setOnClickListener {
                noteClickListener.onClickNote(noteItem)
            }
        }
    }

    interface NoteClickListener {
        fun onClickNote(noteItem: NoteItem)
    }
}