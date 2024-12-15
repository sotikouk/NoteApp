package com.sotkou.noteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sotkou.noteapp.model.Note

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    // Μεταβλητή για την λίστα όλων των notes
    private val allNotes = ArrayList<Note>()

    // Δημιουργούμε την εσωτερική κλάση view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Αρχικοποιούμε τις μεταβλητές που θα χρησιμοποιηθούν
        // για την εμφάνιση των notes στη λίστα.
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Εμφανίζουμε το note_rv_item xml layout για κάθε αντικείμενο στη λίστα.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Γεμίζουμε δεδομένα στο view holder αντικείμενο.
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Last Updated : " + allNotes.get(position).timeStamp)
        // Προσθέτουμε τον click listener για το κουμπί διαγραφή.
        holder.deleteIV.setOnClickListener {
            // καλούμε την interface μεθοδο για την διαγραφή.
            // και περνάμε την θέση του αντικειμένου
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        // Προσθέτουμε τον click listener για το viewholder αντικείμενο
        // στο recycler view και χειριζόμαστε το κλικ στα αντικείμενα της λίστας.
        holder.itemView.setOnClickListener {
            // όμοια καλούμε την interface μεθοδο για την ενημέρωση.
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        // υλοποίηση της getItemCount μέθοδου που
        // απαιτείται απο την RecyclerView.Adapter κλάση.
        return allNotes.size
    }

    // Μέθοδος για την ενημέρωση της λίστας όλων των notes μετά
    // απο αλλαγές στην λίστα.
    fun updateList(newList: List<Note>) {
        // καθαρισμός της λίστας των notes
        allNotes.clear()
        // χρησιμοποιούμε την νέα ενημερωμένη λίστα για τον adapter.
        allNotes.addAll(newList)
        // ειδοποιούμε τον adapter για τις αλλαγές στην λίστα.
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface {
    // δηλώνουμε την abstract μεθοδο για το
    // κλικ στο εικονίδιο της διαγραφής
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    // δηλώνουμε την μέθοδο για ενημέρωση του note
    // στο RecyclerView που έγινε κλίκ
    fun onNoteClick(note: Note)
}
