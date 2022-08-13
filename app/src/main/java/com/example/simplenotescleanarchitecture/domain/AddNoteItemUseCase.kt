package com.example.simplenotescleanarchitecture.domain

class AddNoteItemUseCase(private val noteItemRepository: NotesListRepository) {
    suspend fun addNoteItem(noteItem: NoteItem) {
        noteItemRepository.addNoteItem(noteItem)
    }
}