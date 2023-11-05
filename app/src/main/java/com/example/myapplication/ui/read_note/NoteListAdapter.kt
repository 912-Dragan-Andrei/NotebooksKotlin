package com.example.myapplication.ui.read_note

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemNoteBinding
import com.example.myapplication.data.model.Note
import com.example.myapplication.ui.delete_note.DeleteNoteActivity
import com.example.myapplication.ui.update_note.UpdateNoteActivity
import java.text.SimpleDateFormat
import java.util.Locale

class NoteListAdapter :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffCallback()) {

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(note: Note) {
            binding.nameTextView.text = note.owner
            binding.dateTextView.text = SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(note.date)!!)
            binding.messageTextView.text = note.message
            binding.assignedTextView.text = "assigned to: " + note.assigned

            if (note.urgent) {
                binding.urgentStatusTextView.text = binding.root.context.getString(R.string.urgent_message)
                DrawableCompat.setTint(binding.urgentStatusImage.drawable, ContextCompat.getColor(binding.root.context, R.color.red))
            }
            else {
                binding.urgentStatusTextView.text = binding.root.context.getString(R.string.not_urgent_message)
                DrawableCompat.setTint(binding.urgentStatusImage.drawable, ContextCompat.getColor(binding.root.context, R.color.green))
            }

            binding.editButton.setOnClickListener {
                val intent = Intent(binding.root.context, UpdateNoteActivity::class.java)
                intent.putExtra("noteID", note.id)
                intent.putExtra("notebookID", note.notebookID)
                binding.root.context.startActivity(intent)
            }

            binding.deleteButton.setOnClickListener {
                val intent = Intent(binding.root.context, DeleteNoteActivity::class.java)
                intent.putExtra("noteID", note.id)
                intent.putExtra("notebookID", note.notebookID)
                binding.root.context.startActivity(intent)
            }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return (oldItem.id == newItem.id && oldItem.owner == newItem.owner &&
                    oldItem.message == newItem.message && oldItem.urgent == newItem.urgent &&
                    oldItem.date == newItem.date && oldItem.assigned == newItem.assigned &&
                    oldItem.notebookID == newItem.notebookID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
    }
}
