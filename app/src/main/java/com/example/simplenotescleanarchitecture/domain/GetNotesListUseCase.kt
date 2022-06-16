package com.example.simplenotescleanarchitecture.domain

import androidx.lifecycle.LiveData

class GetNotesListUseCase(private val noteItemRepository: NotesListRepository) {
    fun getNotesList(): LiveData<List<NoteItem>> {
        return noteItemRepository.getNotesList()
    }
}