package com.example.myapplication.data.model

data class Note(
    val id: Long,
    val owner: String,
    val message: String,
    val assigned: String,
    val date: String,
    val urgent: Boolean,
    val notebookID: Long
)
