package com.sotkou.noteapp

import com.sotkou.noteapp.model.Note
import com.sotkou.noteapp.viewmodel.NoteViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    // Δημιουργούμε τις μεταβλητές για τα UI components.
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    // Μια μεταβλητή για το view model class και μια
    // integer για το note id.
    lateinit var viewModel: NoteViewModel
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        // Αρχικοποιούμε το view model class.
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        // Αρχικοποιούμε τις μεταβλητές των UI components.
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        // Λαμβάνουμε τα δεδομένα μέσω του intent
        // που ξεκίνησε την activity.
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) { // Αν ο τύπος ειναι ενημέρωση.
            // Θέτουμε τα δεδομένα στα edit text.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else { // Αλλιώς είναι προσθήκη.
            saveBtn.setText("Save Note")
        }

        // Εναν click listener στο save button.
        saveBtn.setOnClickListener {
            // παίρνουμε τον τίτλο και την περιγραφή από τα edit text.
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()
            // Ελέγχουμε τον τύπο της note αν ειναι ενημέρωση ή προσθήκη.
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            // Οδηγούμαστε στην MainActivity.
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}
