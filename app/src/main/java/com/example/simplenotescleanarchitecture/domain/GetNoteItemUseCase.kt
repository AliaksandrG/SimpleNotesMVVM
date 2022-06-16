package com.example.simplenotescleanarchitecture.domain

class GetNoteItemUseCase(private val noteItemRepository: NotesListRepository) {

    fun getNoteItem(noteItemId:Int):NoteItem{
        return noteItemRepository.getNoteItem(noteItemId)
    }
}