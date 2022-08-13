package com.example.simplenotescleanarchitecture.data

import com.example.simplenotescleanarchitecture.domain.NoteItem

class NoteListMapper {

    fun mapEntityToDbModel(noteItem: NoteItem): NoteItemDmModel = NoteItemDmModel(
        id = noteItem.id,
        title = noteItem.title,
        description = noteItem.description,
        priority = noteItem.priority,
        completed = noteItem.completed
    )

    fun mapDbModelToEntity(noteItemDmModel: NoteItemDmModel): NoteItem = NoteItem(
        id = noteItemDmModel.id,
        title = noteItemDmModel.title,
        description = noteItemDmModel.description,
        priority = noteItemDmModel.priority,
        completed = noteItemDmModel.completed
    )

    fun mapListDbModelToListEntity(list: List<NoteItemDmModel>) = list.map {
        mapDbModelToEntity(it)
    }
}