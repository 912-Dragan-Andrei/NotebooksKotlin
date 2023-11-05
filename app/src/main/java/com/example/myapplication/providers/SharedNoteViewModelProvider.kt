package com.example.myapplication.providers

import com.example.myapplication.util.SharedNoteViewModel

object SharedNoteViewModelProvider {
    @Volatile
    private var sharedNoteViewModel: SharedNoteViewModel? = null

    fun getSharedViewModel(): SharedNoteViewModel {
        return sharedNoteViewModel ?: synchronized(this) {
            sharedNoteViewModel ?: SharedNoteViewModel().also { sharedNoteViewModel = it }
        }
    }
}