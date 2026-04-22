package com.example.simplenotes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotes.R
import com.example.simplenotes.data.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class NoteListActivity : AppCompatActivity() {
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        val recycler = findViewById<RecyclerView>(R.id.recycler_notes)
        val emptyState = findViewById<View>(R.id.text_empty_state)
        val fab = findViewById<FloatingActionButton>(R.id.fab_add_note)

        adapter = NoteAdapter { note ->
            openEditor(note)
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        attachSwipeToDelete(recycler)

        viewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
            emptyState.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        }

        fab.setOnClickListener {
            startActivity(Intent(this, NoteEditActivity::class.java))
        }
    }

    private fun openEditor(note: Note) {
        startActivity(
            Intent(this, NoteEditActivity::class.java)
                .putExtra(NoteEditActivity.EXTRA_NOTE_ID, note.id)
        )
    }

    private fun attachSwipeToDelete(recycler: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val note = adapter.getNoteAt(position)
                viewModel.delete(note)
                Snackbar.make(recycler, getString(R.string.note_deleted), Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo) {
                        viewModel.insert(
                            note.copy(
                                id = 0,
                                createdAt = note.createdAt,
                                updatedAt = System.currentTimeMillis(),
                            )
                        )
                    }
                    .show()
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(recycler)
    }
}
