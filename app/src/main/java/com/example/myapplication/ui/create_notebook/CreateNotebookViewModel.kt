package com.example.myapplication.ui.create_notebook

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Notebook
import com.example.myapplication.providers.NotebookRepositoryProvider
import kotlin.random.Random

class CreateNotebookViewModel() : ViewModel() {
    private var joinID: String? = null
    private val notebookRepository = NotebookRepositoryProvider.getNotebookRepository()

    fun generateJoinCode() {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random

        while (true) {
            joinID = (1..8)
                .map { chars[random.nextInt(chars.length)] }
                .joinToString("")
            joinID = "#$joinID"
            if (notebookRepository.getNotebookID(joinID!!) == null) break
        }
    }

    fun getJoinID(): String? {
        return joinID
    }

    fun createNotebook() : Boolean {
        joinID?.let {
            val notebook = Notebook(id = System.currentTimeMillis(), joinID = it)
            return notebookRepository.addNotebook(notebook)
        }
        return false
    }
}



