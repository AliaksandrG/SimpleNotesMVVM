package com.example.simplenotescleanarchitecture.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotescleanarchitecture.R
import com.example.simplenotescleanarchitecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteItemFragment.OnEditingFinishedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var notesListAdapter: NotesListAdapter

    private var noteItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteItemContainer = binding.noteItemContainer
        initRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.notesList.observe(this) {
            notesListAdapter.submitList(it)
        }
        binding.btnAddNote.setOnClickListener {
            if (isSinglePageMode()) {
                val intent = NoteItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                startFragment(NoteItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun isSinglePageMode(): Boolean {
        return noteItemContainer == null
    }

    private fun startFragment(fragment: Fragment) {
        //delete last fragment in moment onBackPressed()
        supportFragmentManager.popBackStack()

        supportFragmentManager.beginTransaction()
            .add(R.id.note_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_notes)
        notesListAdapter = NotesListAdapter()
        with(recyclerView) {
            adapter = notesListAdapter
            recycledViewPool
                .setMaxRecycledViews(
                    NotesListAdapter.VIEW_TYPE_ENABLED,
                    NotesListAdapter.MAX_PULL_SIZE
                )
            recycledViewPool
                .setMaxRecycledViews(
                    NotesListAdapter.VIEW_TYPE_DISABLED,
                    NotesListAdapter.MAX_PULL_SIZE
                )
        }
        initClickListener()
        initLongClickListener()
        initSwipeListener(recyclerView)

    }

    private fun initSwipeListener(recyclerView: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = notesListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteNoteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initClickListener() {
        notesListAdapter.onNoteItemClickListener = {
            if (isSinglePageMode()) {
                val intent = NoteItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                startFragment(NoteItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun initLongClickListener() {
        notesListAdapter.onNoteItemLongClickListener = {
            viewModel.changeCompleteState(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Saved note", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}