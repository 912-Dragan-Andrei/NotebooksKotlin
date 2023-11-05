package com.example.myapplication.ui.delete_note

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.myapplication.R
import com.example.myapplication.data.model.Note
import com.example.myapplication.databinding.ActivityDeleteNoteBinding
import com.example.myapplication.providers.SharedNoteViewModelProvider
import java.text.SimpleDateFormat
import java.util.Locale

class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteNoteBinding
    private val viewModel = SharedNoteViewModelProvider.getSharedViewModel()

    private var noteToDelete: Note? = null
    private var noteID: Long? = null
    private var notebookID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNoteToDelete()
        setJoinID()
        populateData(noteToDelete)
        confirmButtonClick()
        cancelButtonClick()
        backButtonClick()
    }

    private fun getNoteToDelete() {
        noteID = intent.getLongExtra("noteID", -1)
        if (noteID!!.toInt() == -1) finish()
        noteToDelete = viewModel.getNote(noteID!!)
        if (noteToDelete == null) finish()
    }

    private fun setJoinID() {
        notebookID = intent.getLongExtra("notebookID", -1)
        if (notebookID!!.toInt() == -1) finish()
        val joinID = viewModel.getJoinID(notebookID!!)
        if (joinID == null) finish()
        binding.joinIDTextView.text = joinID
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun populateData(note: Note?) {
        if (note == null) return
        binding.nameTextView.text = note.owner
        binding.dateTextView.text = SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(note.date)!!)
        binding.bodyTextView.text = note.message
        binding.assignedTextView.text = "assigned to: " + note.assigned

        if (note.urgent) {
            binding.urgentStatusTextView.text = binding.root.context.getString(R.string.urgent_message)
            DrawableCompat.setTint(binding.urgentStatusImage.drawable, ContextCompat.getColor(binding.root.context, R.color.red))
        }
        else {
            binding.urgentStatusTextView.text = binding.root.context.getString(R.string.not_urgent_message)
            DrawableCompat.setTint(binding.urgentStatusImage.drawable, ContextCompat.getColor(binding.root.context, R.color.green))
        }
    }

    private fun confirmButtonClick() {
        binding.confirmButton.setOnClickListener {
            viewModel.deleteNote(noteID!!, notebookID!!)
            finish()
        }
    }

    private fun cancelButtonClick() {
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}