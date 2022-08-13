package com.example.simplenotescleanarchitecture.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_items")
data class NoteItemDmModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val completed: Boolean,
)