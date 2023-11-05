package com.example.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // We are using a handler object that is associated with the main thread's looper
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, NotebookOptionsActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}