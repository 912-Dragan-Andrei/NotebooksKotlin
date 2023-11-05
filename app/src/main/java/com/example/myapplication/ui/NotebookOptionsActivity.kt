package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotebookOptionsBinding
import com.example.myapplication.ui.create_notebook.CreateNotebookActivity
import com.example.myapplication.ui.read_note.ReadNotesActivity

class NotebookOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotebookOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotebookOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createNotebookButton.setOnClickListener {
            val intent = Intent(this, CreateNotebookActivity::class.java)
            startActivity(intent)
        }

        displayFeedbackMessage()
        joinNotebookButtonClick()
        createNotebookButtonClick()
    }

    private fun displayFeedbackMessage() {
        val success = intent.getBooleanExtra("success", false)
        val failure = intent.getBooleanExtra("failure", false)
        val feedbackMessage = intent.getStringExtra("feedbackMessage")

        if (success) {
            binding.feedback.visibility = View.VISIBLE
            binding.feedback.setTextColor(ContextCompat.getColor(this, R.color.green))
            binding.feedback.text = feedbackMessage
        }
        else if (failure) {
            binding.feedback.visibility = View.VISIBLE
            binding.feedback.setTextColor(ContextCompat.getColor(this, R.color.red))
            binding.feedback.text = feedbackMessage
        }
        else {
            binding.feedback.visibility = View.GONE
        }
    }

    private fun joinNotebookButtonClick() {
        binding.joinNotebookButton.setOnClickListener {
            val joinID = binding.joinNotebookEditText.text.toString()
            val intent = Intent(this, ReadNotesActivity::class.java)
            intent.putExtra("joinID", joinID)
            startActivity(intent)
        }
    }

    private fun createNotebookButtonClick() {

    }
}


