package com.example.simplenotescleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import com.example.simplenotescleanarchitecture.data.NoteListRepositoryImpl
import com.example.simplenotescleanarchitecture.domain.DeleteNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.EditNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.GetNotesListUseCase
import com.example.simplenotescleanarchitecture.domain.NoteItem

class MainViewModel : ViewModel() {

    private val repository = NoteListRepositoryImpl

    private val getNotesListUseCase = GetNotesListUseCase(repository)
    private val deleteNoteItemUseCase = DeleteNoteItemUseCase(repository)
    private val editNoteItemUseCase = EditNoteItemUseCase(repository)

    val notesList = getNotesListUseCase.getNotesList()

    fun deleteNoteItem(noteItem: NoteItem) {
        deleteNoteItemUseCase.deleteNoteItem(noteItem)
    }

    fun changeCompleteState(noteItem: NoteItem) {
        val newItem = noteItem.copy(completed = !noteItem.completed)
        editNoteItemUseCase.editNoteItem(newItem)
    }
}