package com.example.simplenotescleanarchitecture.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.simplenotescleanarchitecture.domain.NoteItem

class NotesItemDiffCallback: DiffUtil.ItemCallback<NoteItem>() {
    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem == newItem
    }
}