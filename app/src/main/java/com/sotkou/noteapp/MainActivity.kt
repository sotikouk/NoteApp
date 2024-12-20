package com.sotkou.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sotkou.noteapp.model.Note
import com.sotkou.noteapp.viewmodel.NoteViewModel
import java.util.*

// κληρονομεί και απο τις NoteClickDeleteInterface και NoteClickInterface !
class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    // Δηλώνουμε τις μεταβλητές για το recycler view, edit text, button and viewmodel.
    lateinit var viewModel: NoteViewModel
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // αρχικοποιούμε τις μεταβλητές των views
        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)

        // ορίζουμε τον layout
        // manager για το recycler view.
        notesRV.layoutManager = LinearLayoutManager(this)

        // αρχικοποιούμε τον Adapter. Δηλώνουμε this γιατί αφορούν
        // την main activity
        val noteRVAdapter = NoteRVAdapter(this, this, this)

        // Ορίζουμε τον adapter για το recycler view
        notesRV.adapter = noteRVAdapter

        // αρχικοποιούμε το view model
        // Χρησιμοποιούμε AndroidViewModelFactory !
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        // Καλούμε την μέθοδο getAllNotes() από το view model class
        // και παρατηρούμε για αλλαγές στη λίστα όλων των notes.
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                // στις αλλαγες στην λίστα των notes, ενημερώνουμε τον adapter
                noteRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            // Ο click listener για το FAButton για την προσθήκη μιας νέας note.
            // Το intent οδηγεί σε AddEditNoteActivity για την προσθήκη μιας νέας note.
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    // η υλοποίηση της μεθόδου που απαιτείται
    override fun onNoteClick(note: Note) {
        // το intent οδηγεί στην AddEditNoteActivity για την ενημέρωση μιας note.
        // περνάμε τα στοιχεία της note που έγινε το κλικ και θα την ενημερώσουμε.
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    // όμοια
    override fun onDeleteIconClick(note: Note) {
        // Καλούμε την μέθοδο deleteNote από το view model class
        // για την διαγραφή μιας note.
        viewModel.deleteNote(note)
        // εμφάνιση του toast message
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}
