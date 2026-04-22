package com.example.simplenotes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.simplenotes.data.AppDatabase
import com.example.simplenotes.data.Note
import com.example.simplenotes.data.NoteDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NoteDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNote_returnsId() = runBlocking {
        val id = noteDao.insertNote(Note(title = "Test", content = "Content"))
        assertTrue("ID > 0", id > 0)
    }

    @Test
    fun insertAndRetrieveNote() = runBlocking {
        val id = noteDao.insertNote(Note(title = "Title", content = "Body"))
        val note = noteDao.getNoteById(id)
        assertNotNull(note)
        assertEquals("Title", note!!.title)
        assertEquals("Body", note.content)
    }

    @Test
    fun getAllNotes_returnsInsertedNotes() = runBlocking {
        noteDao.insertNote(Note(title = "A", content = "1"))
        noteDao.insertNote(Note(title = "B", content = "2"))
        noteDao.insertNote(Note(title = "C", content = "3"))
        val notes = noteDao.getAllNotes().first()
        assertEquals(3, notes.size)
    }

    @Test
    fun updateNote_changesContent() = runBlocking {
        val note = Note(title = "Old", content = "Before")
        val id = noteDao.insertNote(note)
        noteDao.updateNote(note.copy(id = id, title = "New", content = "After"))
        val result = noteDao.getNoteById(id)
        assertEquals("New", result!!.title)
        assertEquals("After", result.content)
    }

    @Test
    fun deleteNote_removesFromDatabase() = runBlocking {
        val id = noteDao.insertNote(Note(title = "Delete", content = "Bye"))
        noteDao.deleteNoteById(id)
        assertNull(noteDao.getNoteById(id))
    }

    @Test
    fun deleteNote_decreasesCount() = runBlocking {
        noteDao.insertNote(Note(title = "A", content = "1"))
        val id2 = noteDao.insertNote(Note(title = "B", content = "2"))
        assertEquals(2, noteDao.getNotesCount())
        noteDao.deleteNoteById(id2)
        assertEquals(1, noteDao.getNotesCount())
    }

    @Test
    fun getNotesCount_emptyDatabase() = runBlocking {
        assertEquals(0, noteDao.getNotesCount())
    }

    @Test
    fun getAllNotes_orderedByUpdatedDesc() = runBlocking {
        noteDao.insertNote(Note(title = "Old", content = "", updatedAt = 1000))
        noteDao.insertNote(Note(title = "New", content = "", updatedAt = 3000))
        noteDao.insertNote(Note(title = "Mid", content = "", updatedAt = 2000))
        val notes = noteDao.getAllNotes().first()
        assertEquals("New", notes[0].title)
        assertEquals("Mid", notes[1].title)
        assertEquals("Old", notes[2].title)
    }

    @Test
    fun insertNote_emptyTitle() = runBlocking {
        val id = noteDao.insertNote(Note(title = "", content = "No title"))
        assertNotNull(noteDao.getNoteById(id))
    }

    @Test
    fun insertNote_longContent() = runBlocking {
        val long = "A".repeat(10000)
        val id = noteDao.insertNote(Note(title = "Long", content = long))
        assertEquals(10000, noteDao.getNoteById(id)!!.content.length)
    }
}
