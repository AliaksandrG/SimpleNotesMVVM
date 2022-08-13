package com.example.simplenotescleanarchitecture.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenotescleanarchitecture.data.NoteListRepositoryImpl
import com.example.simplenotescleanarchitecture.domain.DeleteNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.EditNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.GetNotesListUseCase
import com.example.simplenotescleanarchitecture.domain.NoteItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteListRepositoryImpl(application)

    private val getNotesListUseCase = GetNotesListUseCase(repository)
    private val deleteNoteItemUseCase = DeleteNoteItemUseCase(repository)
    private val editNoteItemUseCase = EditNoteItemUseCase(repository)

    val notesList = getNotesListUseCase.getNotesList()

    fun deleteNoteItem(noteItem: NoteItem) {
        viewModelScope.launch {
            deleteNoteItemUseCase.deleteNoteItem(noteItem)
        }
    }

    fun changeCompleteState(noteItem: NoteItem) {
        viewModelScope.launch {
            val newItem = noteItem.copy(completed = !noteItem.completed)
            editNoteItemUseCase.editNoteItem(newItem)
        }
    }
}