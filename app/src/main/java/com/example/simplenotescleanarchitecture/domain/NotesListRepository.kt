package com.example.simplenotescleanarchitecture.domain

import androidx.lifecycle.LiveData

interface NotesListRepository {
    fun getNoteItem(noteItemId: Int): NoteItem
    fun addNoteItem(noteItem: NoteItem)
    fun deleteNoteItem(noteItem: NoteItem)
    fun editNoteItem(noteItem: NoteItem)
    fun getNotesList(): LiveData<List<NoteItem>>
}