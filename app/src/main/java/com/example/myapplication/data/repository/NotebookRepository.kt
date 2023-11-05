package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Notebook

class NotebookRepository {

    private val notebookList: MutableList<Notebook> = mutableListOf(Notebook(1, "#ABC"))

    // Create
    fun addNotebook(notebook: Notebook) : Boolean {
        notebookList.add(notebook)
        return true
    }

    // Get the notebookID associated with the joinID
    fun getNotebookID(joinID: String): Long? {
        val associatedNotebooks = notebookList.filter { it.joinID == joinID }.toList()
        if (associatedNotebooks.size != 1) return null
        return associatedNotebooks[0].id
    }

    // Get the joinID associated with the notebookID
    fun getJoinID(notebookID: Long) : String? {
        val associatedNotebooks = notebookList.filter { it.id == notebookID }.toList()
        if (associatedNotebooks.size != 1) return null
        return associatedNotebooks[0].joinID
    }
}