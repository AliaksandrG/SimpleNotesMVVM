package com.example.simplenotescleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.simplenotescleanarchitecture.databinding.ItemNoteDisabledBinding
import com.example.simplenotescleanarchitecture.databinding.ItemNoteEnabledBinding
import com.example.simplenotescleanarchitecture.domain.NoteItem


class NotesListAdapter : ListAdapter<NoteItem, NotesItemViewHolder>(
    NotesItemDiffCallback()
) {

    var onNoteItemLongClickListener: ((NoteItem) -> Unit)? = null
    var onNoteItemClickListener: ((NoteItem) -> Unit)? = null
    private lateinit var binding: ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesItemViewHolder {
        binding = when (viewType) {
            VIEW_TYPE_ENABLED -> {
                ItemNoteEnabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
            VIEW_TYPE_DISABLED -> {
                ItemNoteDisabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
            else -> throw RuntimeException("Unknown view type - $viewType")
        }
        return NotesItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: NotesItemViewHolder, position: Int) {
        val noteItem = getItem(position)
        val binding = viewHolder.binding
        when (binding) {
            is ItemNoteEnabledBinding -> {
                binding.titleNoteTv.text = noteItem.title
                binding.priorityNoteTv.text = noteItem.priority.toString()
                binding.descriptionNoteTv.text = noteItem.description
            }
            is ItemNoteDisabledBinding -> {
                binding.titleNoteTv.text = noteItem.title
                binding.priorityNoteTv.text = noteItem.priority.toString()
                binding.descriptionNoteTv.text = noteItem.description
            }
        }

        binding.root.setOnClickListener {
            onNoteItemClickListener?.invoke(noteItem)
        }

        binding.root.setOnLongClickListener {
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