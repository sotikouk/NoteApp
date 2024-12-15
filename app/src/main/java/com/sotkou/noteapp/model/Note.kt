package com.sotkou.noteapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Δηλώνουμε το όνομα του πίνακα
@Entity(tableName = "notesTable")

// με το @ColumnInfo ορίζουμε το όνομα των στηλών του πίνακα
// και καθορίζουμε την μορφή των attributes του class
class Note (@ColumnInfo(name = "title")val noteTitle :String,
            @ColumnInfo(name = "description")val noteDescription :String,
            @ColumnInfo(name = "timestamp")val timeStamp :String) {
    // ορίζουμε το πρωταρχικό κλειδί του πίνακα
    // και δηλώνουμε οτι το id θα αυξάνεται αυτόματα
    // η αρχική του τιμή ειναι το 0
    @PrimaryKey(autoGenerate = true) var id = 0
}
