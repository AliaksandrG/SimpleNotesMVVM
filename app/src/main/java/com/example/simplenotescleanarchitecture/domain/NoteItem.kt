package com.example.simplenotescleanarchitecture.domain

data class NoteItem(
    val title: String,
    val description: String,
    val priority: Int,
    val completed: Boolean,
    var id: Int = UNDEFINED_ID,
) {

    companion object {
        const val UNDEFINED_ID = -1
    }

}