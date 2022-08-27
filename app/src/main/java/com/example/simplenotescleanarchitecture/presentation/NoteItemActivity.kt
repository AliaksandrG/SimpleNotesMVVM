package com.example.simplenotescleanarchitecture.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.simplenotescleanarchitecture.R
import com.example.simplenotescleanarchitecture.domain.NoteItem


class NoteItemActivity : AppCompatActivity(), NoteItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var noteItemId = NoteItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_item)
        parseIntent()


        val actionbar = supportActionBar
        val textview = TextView(this)
        val layoutparams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        textview.layoutParams = layoutparams
        textview.text = getString(R.string.priority_high)
        textview.textSize = 18f
        actionbar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar?.setCustomView(textview);


        // if savedInstanceState == null -> activity isn't recreated
        if (savedInstanceState == null) {
            initCorrectMode()
        }
    }

    private fun initCorrectMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> NoteItemFragment.newInstanceEditItem(noteItemId)
            MODE_ADD -> NoteItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode = $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.note_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("!intent.hasExtra(EXTRA_SCREEN_MODE)")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode = $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_NOTE_ITEM_ID)) {
                throw RuntimeException("EXTRA_NOTE_ITEM_ID unknown")
            }
            noteItemId = intent.getIntExtra(EXTRA_NOTE_ITEM_ID, NoteItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_NOTE_ITEM_ID = "extra_note_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, NoteItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, NoteItemActivity::class.java)
            intent.putExtra(EXTRA_NOTE_ITEM_ID, id)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}