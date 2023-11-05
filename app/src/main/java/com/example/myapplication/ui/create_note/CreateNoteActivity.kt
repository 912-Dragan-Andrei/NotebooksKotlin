package com.example.myapplication.ui.create_note

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityCreateNoteBinding
import com.example.myapplication.providers.SharedNoteViewModelProvider
import com.example.myapplication.ui.NotebookOptionsActivity

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private val viewModel = SharedNoteViewModelProvider.getSharedViewModel()

    private var joinID: String? = null
    private var notebookID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNotebookID()
        addNoteButtonClick()
        backButtonClick()
    }

    private fun getNotebookID() {
        joinID = intent.getStringExtra("joinID")
        if (joinID == null) {
            val intent = Intent(this, NotebookOptionsActivity::class.java)
            intent.putExtra("feedbackMessage", "There is no notebook associated with this code. Make sure you prefixed your code with #")
            intent.putExtra("failure", true)
            startActivity(intent)
            finish()
        }
        else {
            binding.joinIDTextView.text = joinID
            notebookID = viewModel.getNotebookID(joinID!!)
            if (notebookID == null) {
                val intent = Intent(this, NotebookOptionsActivity::class.java)
                intent.putExtra("feedbackMessage", "There was an error fetching the data associated with this code. Try again later!")
                intent.putExtra("failure", true)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addNoteButtonClick() {
        binding.addNoteButton.setOnClickListener {
            val owner = binding.ownerEditText.text.toString()
            val message = binding.messageEditText.text.toString()
            val assigned = binding.assignedEditText.text.toString()
            val urgent = binding.urgentSwitch.isChecked

            if (owner.isBlank() || message.isBlank()) {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = "The name and the message cannot be left blank!"
            }
            else {
                if (!viewModel.addNote(notebookID!!, owner, message, assigned, urgent)) {
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.errorTextView.text = "There was an error adding the note! Please try again!"
                }
                else finish()
            }
        }
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
