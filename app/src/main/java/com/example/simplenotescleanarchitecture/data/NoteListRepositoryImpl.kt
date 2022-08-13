package com.example.simplenotescleanarchitecture.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.simplenotescleanarchitecture.domain.NoteItem
import com.example.simplenotescleanarchitecture.domain.NotesListRepository

class NoteListRepositoryImpl(application: Application) : NotesListRepository {

    private val noteListDao = AppDatabase.getInstance(application).noteListDao()
    private val mapper = NoteListMapper()

    override suspend fun getNoteItem(noteItemId: Int): NoteItem {
        val dbModel = noteListDao.getNoteItem(noteItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun addNoteItem(noteItem: NoteItem) {
        noteListDao.addNoteItem(mapper.mapEntityToDbModel(noteItem))
    }

    override suspend fun deleteNoteItem(noteItem: NoteItem) {
        noteListDao.deleteNoteItem(noteItem.id)
    }

    override suspend fun editNoteItem(noteItem: NoteItem) {
        noteListDao.addNoteItem(mapper.mapEntityToDbModel(noteItem))
    }

//    override fun getNotesList(): LiveData<List<NoteItem>> {
//        return MediatorLiveData<List<NoteItem>>().apply {
//            addSource(noteListDao.getNoteList()){
//                if (it.size > 10) {
//                    value = mapper.mapListDbModelToListEntity(it)
//                }
//            }
//        }
//    }

    override fun getNotesList(): LiveData<List<NoteItem>> {
        return Transformations.map(noteListDao.getNoteList()) {
            mapper.mapListDbModelToListEntity(it)
        }
    }
}