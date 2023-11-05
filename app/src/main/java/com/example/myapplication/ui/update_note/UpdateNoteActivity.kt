// UpdateNoteActivity.kt
package com.example.myapplication.ui.update_note

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.model.Note
import com.example.myapplication.databinding.ActivityUpdateNoteBinding
import com.example.myapplication.providers.SharedNoteViewModelProvider

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private val viewModel = SharedNoteViewModelProvider.getSharedViewModel()
    private var noteToUpdate: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNoteToUpdate()
        setJoinID()
        populateData(noteToUpdate)
        updateNoteButtonClick()
        backButtonClick()
    }

    private fun getNoteToUpdate() {
        val noteID = intent.getLongExtra("noteID", -1)
        if (noteID.toInt() == -1) finish()
        noteToUpdate = viewModel.getNote(noteID)
        if (noteToUpdate == null) finish()
    }

    private fun setJoinID() {
        val notebookID = intent.getLongExtra("notebookID", -1)
        if (notebookID.toInt() == -1) finish()
        val joinID = viewModel.getJoinID(notebookID)
        if (joinID == null) finish()
        binding.joinIDTextView.text = joinID
    }

    private fun populateData(noteToUpdate: Note?) {
        // Populate the fields
        noteToUpdate?.let {
            binding.messageEditText.setText(it.message)
            binding.assignedEditText.setText(it.assigned)
            binding.ownerEditText.setText(it.owner)
            binding.urgentSwitch.isChecked = it.urgent
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateNoteButtonClick() {
        binding.updateNoteButton.setOnClickListener {
            if (binding.ownerEditText.text.isBlank() || binding.messageEditText.text.isBlank()) {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = "The name and the message cannot be left blank!"
            }
            else {
                val updatedNote = noteToUpdate?.copy(
                    owner = binding.ownerEditText.text.toString(),
                    message = binding.messageEditText.text.toString(),
                    assigned = binding.assignedEditText.text.toString(),
                    urgent = binding.urgentSwitch.isChecked
                )
                updatedNote?.let {
                    if (!viewModel.updateNote(it)) {
                        binding.errorTextView.visibility = View.VISIBLE
                        binding.errorTextView.text = "There was an error updating the note! Please try again!"
                    }
                    else finish()
                }
            }
        }
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
