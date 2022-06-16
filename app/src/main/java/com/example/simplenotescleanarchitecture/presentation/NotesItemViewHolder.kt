package com.example.simplenotescleanarchitecture.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotescleanarchitecture.R

class NotesItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle: TextView = view.findViewById(R.id.title_note_tv)
    val tvPriority: TextView = view.findViewById(R.id.priority_note_tv)
    val tvDescription: TextView = view.findViewById(R.id.description_note_tv)
}