package com.example.simplenotescleanarchitecture.domain

import androidx.lifecycle.LiveData

interface NotesListRepository {
    suspend fun getNoteItem(noteItemId: Int): NoteItem
    suspend fun addNoteItem(noteItem: NoteItem)
    suspend fun deleteNoteItem(noteItem: NoteItem)
    suspend fun editNoteItem(noteItem: NoteItem)
    fun getNotesList(): LiveData<List<NoteItem>>
}