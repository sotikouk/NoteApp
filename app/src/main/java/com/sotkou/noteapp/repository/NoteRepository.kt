package com.sotkou.noteapp.repository

import com.sotkou.noteapp.dao.NotesDao
import com.sotkou.noteapp.model.Note
import androidx.lifecycle.LiveData


class NoteRepository(private val notesDao: NotesDao) {

    // παρακάτω δημιουργούμε μια μεταβλητή για την λίστα όλων των notes
    // την οποία παίρνουμε απο την notesDao μέθοδο getAllNotes()
    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    // παρακάτω δημιουργόυμε μια insert μέθοδο που θα χρησιμοποιηθεί
    // για να προσθέσουμε ενα note στη βάση δεδομένων.
    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    // όμοια για την διαγραφή ενος note απο την βάση δεδομένων.
    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    // και για την ενημέρωση μιας note απο την βάση δεδομένων.
    suspend fun update(note: Note){
        notesDao.update(note)
    }
}
