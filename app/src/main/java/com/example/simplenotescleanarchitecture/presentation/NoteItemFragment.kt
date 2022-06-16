package com.example.simplenotescleanarchitecture.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplenotescleanarchitecture.R
import com.example.simplenotescleanarchitecture.databinding.FragmentNoteItemBinding
import com.example.simplenotescleanarchitecture.domain.NoteItem

class NoteItemFragment : Fragment() {

    private var _binding: FragmentNoteItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = MODE_UNKNOWN
    private var noteItemId: Int = NoteItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity not impl listener OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[NoteItemViewModel::class.java]
        addTextChangeListeners()
        initCorrectMode()
        observeViewModel()
    }

    private fun initCorrectMode() {
        when (screenMode) {
            MODE_EDIT -> startEditMode()
            MODE_ADD -> startAddMode()
        }
    }

    private fun startAddMode() {
        binding.saveBtn.setOnClickListener {
            viewModel.addNewItem(
                inputTitle = binding.etTitle.text?.toString(),
                inputDescription = binding.etDescription.text?.toString(),
                inputPriority = (binding.prioritySpinner.selectedItemPosition + 1).toString()
            )
        }
    }

    private fun startEditMode() {
        viewModel.getNoteItem(noteItemId)
        viewModel.noteItem.observe(viewLifecycleOwner) {
            binding.apply {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
                // index spinner started from 0, index note started from 1
                prioritySpinner.setSelection(it.priority - 1)
            }
        }

        binding.saveBtn.setOnClickListener {
            viewModel.editNoteItem(
                inputTitle = binding.etTitle.text?.toString(),
                inputDescription = binding.etDescription.text?.toString(),
                inputPriority = (binding.prioritySpinner.selectedItemPosition + 1).toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputTitle.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_field)
            } else {
                null
            }
            binding.tilTitle.error = message
        }

        viewModel.errorInputDescription.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_field)
            } else {
                null
            }
            binding.tilDescription.error = message
        }

        viewModel.closeScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun addTextChangeListeners() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputTitle()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDescription()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("!intent.hasExtra(EXTRA_SCREEN_MODE)")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode = $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(NOTE_ITEM_ID)) {
                throw RuntimeException("EXTRA_NOTE_ITEM_ID unknown")
            }
            noteItemId = args.getInt(NOTE_ITEM_ID, NoteItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val NOTE_ITEM_ID = "extra_note_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): NoteItemFragment {
            val args = Bundle()
            args.putString(SCREEN_MODE, MODE_ADD)
            val fragment = NoteItemFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstanceEditItem(noteItemId: Int): NoteItemFragment {
            val args = Bundle()
            args.putString(SCREEN_MODE, MODE_EDIT)
            args.putInt(NOTE_ITEM_ID, noteItemId)
            val fragment = NoteItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}