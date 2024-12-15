package com.sotkou.noteapp.dao

import com.sotkou.noteapp.model.Note
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {
        // Singleton αποτρέπει πολλαπλές
        // οντότητες για το άνοιγμα της βάσης δεδομένων
        // ταυτόχρονα.
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            // εάν  INSTANCE δεν είναι null, στείλε το επιστροφή,
            // εάν είναι δημιούργησε την βάση δεδομένων.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
