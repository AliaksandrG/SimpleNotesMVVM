package com.example.simplenotescleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.simplenotescleanarchitecture.R
import com.example.simplenotescleanarchitecture.domain.NoteItem


class NotesListAdapter : ListAdapter<NoteItem, NotesItemViewHolder>(
    NotesItemDiffCallback()
) {

    var onNoteItemLongClickListener: ((NoteItem) -> Unit)? = null
    var onNoteItemClickListener: ((NoteItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_note_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_note_disabled
            else -> throw RuntimeException("Unknown view type - $viewType")
        }
        val view =
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return NotesItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NotesItemViewHolder, position: Int) {
        val noteItem = getItem(position)
        viewHolder.tvTitle.text = noteItem.title
        viewHolder.tvPriority.text = noteItem.priority.toString()
        viewHolder.tvDescription.text = noteItem.description

        viewHolder.view.setOnClickListener {
            onNoteItemClickListener?.invoke(noteItem)
        }

        viewHolder.view.setOnLongClickListener {
            onNoteItemLongClickListener?.invoke(noteItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.completed) {
            VIEW_TYPE_DISABLED
        } else {
            VIEW_TYPE_ENABLED
        }
    }


    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_PULL_SIZE = 30
    }
}