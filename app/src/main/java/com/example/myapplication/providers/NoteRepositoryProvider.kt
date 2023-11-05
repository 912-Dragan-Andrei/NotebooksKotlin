package com.example.myapplication.providers

import com.example.myapplication.data.repository.NoteRepository

object NoteRepositoryProvider {
    @Volatile
    private var noteRepository: NoteRepository? = null

    fun getNoteRepository(): NoteRepository {
        return noteRepository ?: synchronized(this) {
            noteRepository ?: NoteRepository().also { noteRepository = it }
        }
    }
}