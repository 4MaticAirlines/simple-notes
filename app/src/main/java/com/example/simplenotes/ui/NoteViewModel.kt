package com.example.simplenotes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplenotes.data.AppDatabase
import com.example.simplenotes.data.Note
import com.example.simplenotes.data.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val dao = AppDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes.asLiveData()
    }

    fun insert(note: Note) = viewModelScope.launch { repository.insert(note) }

    fun update(note: Note) = viewModelScope.launch { repository.update(note) }

    fun delete(note: Note) = viewModelScope.launch { repository.delete(note) }

    suspend fun getNoteById(id: Long): Note? = repository.getNoteById(id)
}
