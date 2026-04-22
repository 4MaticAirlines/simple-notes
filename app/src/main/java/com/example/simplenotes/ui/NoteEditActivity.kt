package com.example.simplenotes.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.simplenotes.R
import com.example.simplenotes.data.Note
import kotlinx.coroutines.launch

class NoteEditActivity : AppCompatActivity() {
    private val viewModel: NoteViewModel by viewModels()
    private var noteId: Long = 0L
    private var existingNote: Note? = null

    private lateinit var titleEdit: EditText
    private lateinit var contentEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        titleEdit = findViewById(R.id.edit_title)
        contentEdit = findViewById(R.id.edit_content)
        noteId = intent.getLongExtra(EXTRA_NOTE_ID, 0L)

        if (noteId != 0L) {
            lifecycleScope.launch {
                existingNote = viewModel.getNoteById(noteId)
                existingNote?.let { note ->
                    titleEdit.setText(note.title)
                    contentEdit.setText(note.content)
                    supportActionBar?.title = getString(R.string.edit_note_title)
                }
            }
        } else {
            supportActionBar?.title = getString(R.string.new_note_title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_save -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = titleEdit.text.toString().trim()
        val content = contentEdit.text.toString().trim()

        if (title.isBlank()) {
            titleEdit.error = getString(R.string.error_title_required)
            return
        }

        val now = System.currentTimeMillis()
        val savedNote = existingNote?.copy(
            title = title,
            content = content,
            updatedAt = now,
        ) ?: Note(
            title = title,
            content = content,
            createdAt = now,
            updatedAt = now,
        )

        if (existingNote == null) {
            viewModel.insert(savedNote)
        } else {
            viewModel.update(savedNote)
        }
        finish()
    }

    companion object {
        const val EXTRA_NOTE_ID = "note_id"
    }
}
