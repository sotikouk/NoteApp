package com.sotkou.noteapp.viewmodel

import com.sotkou.noteapp.dao.NoteDatabase
import com.sotkou.noteapp.model.Note
import com.sotkou.noteapp.repository.NoteRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) :AndroidViewModel(application) {

    // δημιουργούμε μια μεταβλητή που θα περιέχει την λίστα όλων των notes
    val allNotes : LiveData<List<Note>>
    val repository : NoteRepository

    // αρψικοποιούμε το dao, repository και την allNotes μεταβλητή
    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    // μέθοδος διαγραφής. Καλούμε την μέθοδο διαγραφής από το repository
    // viewModelScope: διευκολύνει την εκτέλεση ασύγχρονων εργασιών με
    // ασφαλή διαχείριση κύκλου ζωής μέσα στο ViewModel
    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    // όμοια για την ενημέρωση μιας note.
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    // επίσης για την προσθήκη μιας note.
    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}
