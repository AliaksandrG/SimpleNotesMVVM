package com.example.simplenotescleanarchitecture.domain

class DeleteNoteItemUseCase(private val noteItemRepository: NotesListRepository) {
    suspend fun deleteNoteItem(noteItem: NoteItem) {
        noteItemRepository.deleteNoteItem(noteItem)
    }
}