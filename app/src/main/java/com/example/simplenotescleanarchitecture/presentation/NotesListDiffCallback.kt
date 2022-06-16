package com.example.simplenotescleanarchitecture.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.simplenotescleanarchitecture.domain.NoteItem

// NOT USE
class NotesListDiffCallback(
    private val oldList:List<NoteItem>,
    private val newList:List<NoteItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem == newItem
    }
}