package com.example.myapplication.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Note
import com.example.myapplication.providers.NoteRepositoryProvider
import com.example.myapplication.providers.NotebookRepositoryProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SharedNoteViewModel : ViewModel() {
    // Get the repositories
    private val noteRepository = NoteRepositoryProvider.getNoteRepository()
    private val notebookRepository = NotebookRepositoryProvider.getNotebookRepository()

    // Initiate the LiveData object
    val noteList: MutableLiveData<MutableList<Note>> = MutableLiveData()

    init {
        // Initialize the LiveData with an empty list
        noteList.value = mutableListOf()
    }

    // Load the notes for a given notebookID
    fun loadNotesForNotebook(notebookID: Long) {
        noteList.postValue(noteRepository.getAllNotesForNotebook(notebookID).toMutableList())
    }

    // Get the notebookID associated with a joinID
    fun getNotebookID(joinID: String) : Long? {
        return notebookRepository.getNotebookID(joinID)
    }

    // Get the joinID associated with a notebookID
    fun getJoinID(notebookID: Long) : String? {
        return notebookRepository.getJoinID(notebookID)
    }

    // Create a note
    fun addNote(notebookID: Long, owner: String, message: String, assigned: String, urgent: Boolean): Boolean {
        val currentDate = getCurrentDate()
        val newNote = Note(
            id = System.currentTimeMillis(),
            owner = owner,
            message = message,
            assigned = assigned,
            date = currentDate,
            urgent = urgent,
            notebookID = notebookID
        )
        if (noteRepository.addNote(newNote)) {
            val currentList = noteList.value.orEmpty().toMutableList()
            currentList.add(0, newNote)
            noteList.postValue(currentList)
            return true
        }
        return false
    }

    // Update a note
    fun updateNote(note: Note): Boolean {
        val updatedNote = note.copy(
            date = getCurrentDate()
        )
        if (noteRepository.updateNote(updatedNote)) {
            val currentList = noteList.value.orEmpty().toMutableList()
            val index = currentList.indexOfFirst { it.id == updatedNote.id }
            if (index != -1) {
                currentList.removeAt(index)
                currentList.add(0, updatedNote)
                noteList.postValue(currentList)
            }
            return true
        }
        return false
    }

    // Delete a note
    fun deleteNote(noteID: Long, notebookID: Long): Boolean {
        if (noteRepository.deleteNote(noteID)) {
            val currentList = noteList.value.orEmpty().toMutableList()
            val index = currentList.indexOfFirst { it.id == noteID }
            if (index != -1) {
                currentList.removeAt(index)
                noteList.postValue(currentList)
            }
            return true
        }
        return false
    }

    // Other utils
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getNote(noteID: Long): Note? {
        return noteRepository.getNote(noteID)
    }
}