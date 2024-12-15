package com.sotkou.noteapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sotkou.noteapp.model.Note

// annotation για την dao class.
@Dao
interface NotesDao {

    // παρακάτω είναι η insert μέθοδος για
    // να προσθέσουμε ένα καινούργιο entry στην βάση δεδομένων.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    // παρακάτω ειναι η μέθοδος διαγραφή.
    @Delete
    suspend fun delete(note: Note)

    // παρακάτω είναι η μέθοδος αναζήτησης όλων των notes
    // απο την βάση δεδομένων και ορίζουμε ενα query.
    // το query επιστρέφει όλες τις σειρές απο την βάση
    // με σειρά αυξανόμενη με βάση το id.
    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    // παρακάτω η μέθοδος ενημέρωσης ενος note.
    @Update
    suspend fun update(note: Note)

}
