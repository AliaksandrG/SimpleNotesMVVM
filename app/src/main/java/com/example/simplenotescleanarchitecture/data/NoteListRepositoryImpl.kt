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
    private val randomTitle =
        listOf("rebuild the kitchen", "buy food", "apply for a visa")
    private val randomDescription =
        listOf("go to ...", "description test ${Random.nextInt(1000)}", "test text")

    init {
        for (i in 0 until 50) {
            val item = NoteItem(
                title = randomTitle.random(),
                description = randomDescription.random(),
                priority = Random.nextInt(1, 5),
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