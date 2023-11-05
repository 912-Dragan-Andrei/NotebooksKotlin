package com.example.myapplication.ui.create_notebook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityCreateNotebookBinding
import com.example.myapplication.ui.NotebookOptionsActivity

class CreateNotebookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNotebookBinding
    private val viewModel: CreateNotebookViewModel by lazy { CreateNotebookViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNotebookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateAndDisplayJoinID()
        confirmButtonClick()
        cancelButtonClick()
    }

    private fun generateAndDisplayJoinID() {
        viewModel.generateJoinCode()
        val joinID = viewModel.getJoinID()

        if (joinID == null) {
            val intent = Intent(this, NotebookOptionsActivity::class.java)
            intent.putExtra("failure", true)
            intent.putExtra("feedbackMessage", "There was an error creating your notebook. Please try again!")
            startActivity(intent)
            finish()
        }

        binding.joinCodeText.text = viewModel.getJoinID()
    }

    private fun confirmButtonClick() {
        binding.confirmButton.setOnClickListener {
            val intent = Intent(this, NotebookOptionsActivity::class.java)

            if (viewModel.createNotebook()) {
                intent.putExtra("success", true)
                intent.putExtra("feedbackMessage", "Notebook created successfully!")
            }
            else {
                intent.putExtra("failure", true)
                intent.putExtra("feedbackMessage", "There was an error creating your notebook. Please try again!")
            }

            startActivity(intent)
            finish()
        }
    }

    private fun cancelButtonClick() {
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
}