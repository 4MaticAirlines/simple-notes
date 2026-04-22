package com.example.simplenotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotes.R
import com.example.simplenotes.data.Note

class NoteAdapter(
    private val onClick: (Note) -> Unit,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var items: List<Note> = emptyList()

    fun submitList(notes: List<Note>) {
        items = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text_note_title)
        private val content: TextView = itemView.findViewById(R.id.text_note_content)
        private val date: TextView = itemView.findViewById(R.id.text_note_date)

        fun bind(note: Note) {
            title.text = note.title
            content.text = note.content.take(50).ifBlank { itemView.context.getString(R.string.note_without_text) }
            date.text = DateFormatter.format(note.updatedAt)
            itemView.setOnClickListener { onClick(note) }
        }
    }
}
