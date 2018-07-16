package me.fabiooliveira.getnotes.feature.notesList.ui.adapter

import android.content.Context
import androidx.annotation.Nullable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import me.fabiooliveira.getnotes.model.entity.Note
import android.view.LayoutInflater
import me.fabiooliveira.getnotes.R
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.expandable_list_header.view.*
import kotlinx.android.synthetic.main.expandable_list_item.view.*
import me.fabiooliveira.getnotes.feature.notesList.listener.NoteInfoInterface

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class NoteExpandableListAdapter(private val context: Context, private val expandableNoteTitlesList: List<String>,
                                private var expandableNoteListDetail: Map<String, MutableList<Note>>): BaseExpandableListAdapter() {

    private var noteInfoInterface: NoteInfoInterface? = null

    init {
        sortList()
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return expandableNoteListDetail[expandableNoteTitlesList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val note = getChild(listPosition, expandedListPosition) as Note
        if (view == null) {
            val layoutInflater = this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.expandable_list_item, null)
        }
        view?.let {
            it.tvTitle.text = if(note.title.isEmpty()) context.resources.getText(R.string.note_list_untitled) else note.title
            it.tvContentDescription.text = if(note.contentDescription.isEmpty()) context.resources.getText(R.string.note_list_undescriptioned) else note.contentDescription
            it.ibPopMenu.setOnClickListener {
                noteInfoInterface?.onPopupMenuClick(it.ibPopMenu, note)
            }
            when(note.isDone) {
                true ->  {
                    it.ivDone.visibility = View.VISIBLE
                    it.tvDone.visibility = View.VISIBLE
                    it.tvDraft.visibility = View.GONE
                    it.clCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen))
                }
                false -> {
                    it.ivDone.visibility = View.GONE
                    it.tvDone.visibility = View.GONE
                    it.clCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))

                    if(note.title.isEmpty() || note.contentDescription.isEmpty()) {
                        it.tvDraft.visibility = View.VISIBLE
                        it.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.colorGrey))
                        it.tvContentDescription.setTextColor(ContextCompat.getColor(context, R.color.colorGrey))
                    } else {
                        it.tvDraft.visibility = View.GONE
                    }
                }
            }
            when(note.relevance) {
                in 66..100 -> it.ivRelevance.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRelevanceFirst))
                in 33..65 -> it.ivRelevance.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRelevanceSecond))
                else -> it.ivRelevance.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRelevanceThird ))
            }
        }
        return view!!
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.expandableNoteListDetail[this.expandableNoteTitlesList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.expandableNoteTitlesList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.expandableNoteTitlesList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val listTitle = getGroup(listPosition) as String

        if (view == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.expandable_list_header, null)
        }

        view?.let {
            it.tvTitleHeader.text = listTitle

            if(isExpanded) {
                it.imageView.setImageResource(R.drawable.arrow_up)
                it.cl_header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            } else {
                it.imageView.setImageResource(R.drawable.arrow_down)
                it.cl_header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }
        return view!!
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    fun setNoteInfoInterface(@Nullable noteInfoInterface: NoteInfoInterface) {
        this.noteInfoInterface = noteInfoInterface
    }

    @Suppress("UNCHECKED_CAST")
    private fun sortList(){
        var expandableNoteListDetailAux = mutableMapOf<String, List<Note>>()
        expandableNoteListDetail.entries.forEach {
            val list = it.value.sortedWith(Comparator { n1, n2 -> n1.relevance - n2.relevance })
            expandableNoteListDetailAux[it.key] = list
        }
        expandableNoteListDetail = expandableNoteListDetailAux as Map<String, MutableList<Note>>
    }
}