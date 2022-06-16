package com.example.simplenotescleanarchitecture.domain

class DeleteNoteItemUseCase(private val noteItemRepository: NotesListRepository) {
    fun deleteNoteItem(noteItem: NoteItem) {
        noteItemRepository.deleteNoteItem(noteItem)
    }
}