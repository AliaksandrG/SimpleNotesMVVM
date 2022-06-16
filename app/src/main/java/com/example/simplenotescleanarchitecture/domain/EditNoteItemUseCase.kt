package com.example.simplenotescleanarchitecture.domain

class EditNoteItemUseCase(private val noteItemRepository: NotesListRepository) {
    fun editNoteItem(noteItem: NoteItem) {
        noteItemRepository.editNoteItem(noteItem)
    }
}