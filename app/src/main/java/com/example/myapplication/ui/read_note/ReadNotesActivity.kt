package com.example.myapplication.ui.read_note

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityReadNotesBinding
import com.example.myapplication.providers.SharedNoteViewModelProvider
import com.example.myapplication.ui.NotebookOptionsActivity
import com.example.myapplication.ui.create_note.CreateNoteActivity

class ReadNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadNotesBinding
    private val viewModel = SharedNoteViewModelProvider.getSharedViewModel()

    private var joinID: String? = null
    private var notebookID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNotebookID()
        setupRecycleView()
        Log.d("Read", "read")
        viewModel.loadNotesForNotebook(notebookID!!)
        addNewNoteButtonClick()
        backButtonClick()
    }

    private fun setupRecycleView() {
        // Set up the RecyclerView
        val adapter = NoteListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the LiveData from the ViewModel
        viewModel.noteList.observe(this) { notes ->
            adapter.submitList(notes)
        }
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

    private fun addNewNoteButtonClick() {
        binding.addNewNoteButton.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            intent.putExtra("joinID", joinID)
            startActivity(intent)
        }
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, NotebookOptionsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}