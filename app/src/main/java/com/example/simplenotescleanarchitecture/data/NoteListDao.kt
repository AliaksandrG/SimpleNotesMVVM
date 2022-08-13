package com.example.simplenotescleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteListDao {

    @Query("SELECT * FROM note_items")
    fun getNotesList(): LiveData<List<NoteItemDmModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNoteItem(noteItemDmModel: NoteItemDmModel)

    @Query("DELETE FROM note_items WHERE id=:noteItemId")
    fun deleteNoteItem(noteItemId: Int)

    @Query("SELECT * FROM note_items WHERE id=:noteItemId LIMIT 1")
    fun getNoteList(noteItemId: Int) : NoteItemDmModel

}