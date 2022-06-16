package com.example.simplenotescleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplenotescleanarchitecture.domain.NoteItem
import com.example.simplenotescleanarchitecture.domain.NotesListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object NoteListRepositoryImpl : NotesListRepository {

    private val notesListLiveData = MutableLiveData<List<NoteItem>>()
    private val notesList = sortedSetOf<NoteItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = NoteItem(
                title = "title $i",
                description = i.toString(),
                priority = 3,
                completed = Random.nextBoolean()
            )
            addNoteItem(item)
        }
    }

    override fun getNoteItem(noteItemId: Int): NoteItem {
        return notesList.find { it.id == noteItemId }
            ?: throw RuntimeException("element with $noteItemId not found")
    }

    override fun addNoteItem(noteItem: NoteItem) {
        if (noteItem.id == NoteItem.UNDEFINED_ID) {
            noteItem.id = autoIncrementId++
        }
        notesList.add(noteItem)
        updateList()
    }

    override fun deleteNoteItem(noteItem: NoteItem) {
        notesList.remove(noteItem)
        updateList()
    }

    override fun editNoteItem(noteItem: NoteItem) {
        val oldElement = getNoteItem(noteItem.id)
        notesList.remove(oldElement)
        addNoteItem(noteItem)
    }

    override fun getNotesList(): LiveData<List<NoteItem>> {
        return notesListLiveData
    }

    private fun updateList() {
        notesListLiveData.value = notesList.toList()
    }
}