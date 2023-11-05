package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NoteRepository {
    private val noteList: MutableList<Note> = mutableListOf()

    // Create
    fun addNote(note: Note): Boolean {
        noteList.add(note)
        return true
    }

    // Read
    fun getAllNotesForNotebook(notebookID: Long): List<Note> {
        return noteList.filter { it.notebookID == notebookID }
            .sortedByDescending { SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(it.date) }
            .toList()
    }

    // Update
    fun updateNote(updatedNote: Note): Boolean {
        val existingNote = noteList.find { it.id == updatedNote.id }
        existingNote?.let {
            val mutableNote = it.copy(
                owner = updatedNote.owner,
                message = updatedNote.message,
                assigned = updatedNote.assigned,
                date = updatedNote.date,
                urgent = updatedNote.urgent,
                notebookID = updatedNote.notebookID
            )
            noteList[noteList.indexOf(it)] = mutableNote
        }
        return true
    }

    // Get note by ID
    fun getNote(noteID: Long): Note? {
        val correspondingNotes = noteList.filter { it.id == noteID }.toList()
        if (correspondingNotes.size != 1) return null
        return correspondingNotes[0]
    }

    // Delete
    fun deleteNote(noteID: Long): Boolean {
        noteList.removeAll { it.id == noteID }
        return true
    }
}