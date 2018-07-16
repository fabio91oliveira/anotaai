package me.fabiooliveira.getnotes.feature.notesList.listener

import android.view.View
import me.fabiooliveira.getnotes.model.entity.Note

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

interface NoteInfoInterface {
    fun onPopupMenuClick(view: View, note: Note)
}