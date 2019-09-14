package com.riluq.mynoteapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.riluq.mynoteapp.database.Note
import com.riluq.mynoteapp.database.NoteDao
import com.riluq.mynoteapp.database.NoteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private var noteDao: NoteDao? = null
    private var executorService: ExecutorService? = null

    init {
        executorService = Executors.newSingleThreadExecutor()

        val db = NoteDatabase.getDatabase(application)
        noteDao = db.noteDao
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return noteDao?.getAllNotes()
    }

    fun insert(note: Note) {
        executorService?.execute {
            noteDao?.insert(note)
        }
    }

    fun delete(note: Note) {
        executorService?.execute {
            noteDao?.delete(note)
        }
    }

    fun update(note: Note) {
        executorService?.execute {
            noteDao?.update(note)
        }
    }

}