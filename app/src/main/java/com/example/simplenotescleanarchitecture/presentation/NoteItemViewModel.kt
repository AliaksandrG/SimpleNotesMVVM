package com.example.simplenotescleanarchitecture.presentation


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplenotescleanarchitecture.data.NoteListRepositoryImpl
import com.example.simplenotescleanarchitecture.domain.AddNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.EditNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.GetNoteItemUseCase
import com.example.simplenotescleanarchitecture.domain.NoteItem
import kotlinx.coroutines.launch

class NoteItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteListRepositoryImpl(application)

    private val getNoteItemUseCase = GetNoteItemUseCase(repository)
    private val addNoteItemUseCase = AddNoteItemUseCase(repository)
    private val editNoteItemUseCase = EditNoteItemUseCase(repository)

    private val _errorInputTitle = MutableLiveData<Boolean>()
    val errorInputTitle: LiveData<Boolean>
        get() = _errorInputTitle

    private val _errorInputDescription = MutableLiveData<Boolean>()
    val errorInputDescription: LiveData<Boolean>
        get() = _errorInputDescription

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    private val _noteItem = MutableLiveData<NoteItem>()
    val noteItem: LiveData<NoteItem>
        get() = _noteItem

    fun getNoteItem(noteItemId: Int) {
        viewModelScope.launch {
            val item = getNoteItemUseCase.getNoteItem(noteItemId)
            _noteItem.value = item
        }
    }

    fun addNewItem(inputTitle: String?, inputDescription: String?, inputPriority: String?) {
        val title = parseText(inputTitle)
        val description = parseText(inputDescription)
        val isFieldsValid = validateInput(title, description, inputPriority!!.toInt())
        if (isFieldsValid) {
            viewModelScope.launch {
                val noteItem = NoteItem(
                    title = title,
                    description = description,
                    priority = inputPriority.toInt(),
                    completed = false
                )
                addNoteItemUseCase.addNoteItem(noteItem)
                finishScreen()
            }
        }

    }

    fun editNoteItem(inputTitle: String?, inputDescription: String?, inputPriority: String?) {
        val title = parseText(inputTitle)
        val description = parseText(inputDescription)
        val isFieldsValid = validateInput(title, description, inputPriority!!.toInt())
        if (isFieldsValid) {
            viewModelScope.launch {
                _noteItem.value?.let {
                    val item = it.copy(
                        title = title,
                        description = description,
                        priority = inputPriority.toInt(),
                        completed = false
                    )
                    editNoteItemUseCase.editNoteItem(item)
                    finishScreen()
                }
            }
        }
    }

    private fun parseText(text: String?): String {
        return text?.trim() ?: ""
    }

    private fun validateInput(title: String, description: String, priority: Int): Boolean {
        var result = true
        if (title.isBlank()) {
            _errorInputTitle.value = true
            result = false
        }
        if (description.isBlank()) {
            _errorInputDescription.value = true

            result = false
        }

        return result
    }

    fun resetErrorInputTitle() {
        _errorInputTitle.value = false
    }

    fun resetErrorInputDescription() {
        _errorInputDescription.value = false
    }

    private fun finishScreen() {
        _closeScreen.value = Unit
    }
}