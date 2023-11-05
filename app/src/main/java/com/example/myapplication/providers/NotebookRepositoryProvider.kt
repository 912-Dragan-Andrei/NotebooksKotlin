package com.example.myapplication.providers

import com.example.myapplication.data.repository.NoteRepository
import com.example.myapplication.data.repository.NotebookRepository


object NotebookRepositoryProvider {
    @Volatile
    private var notebookRepository: NotebookRepository? = null

    fun getNotebookRepository(): NotebookRepository {
        return notebookRepository ?: synchronized(this) {
            notebookRepository ?: NotebookRepository().also { notebookRepository = it }
        }
    }
}