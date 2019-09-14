package com.riluq.mynoteapp.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riluq.mynoteapp.database.Note
import com.riluq.mynoteapp.repository.NoteRepository

class NoteAddUpdateViewModel(application: Application): ViewModel() {

    private var noteRepository: NoteRepository? = null

    init {
        noteRepository = NoteRepository(application)
    }

    fun insert(note: Note) {
        noteRepository?.insert(note)
    }

    fun update(note: Note) {
        noteRepository?.update(note)
    }

    fun delete(note: Note) {
        noteRepository?.delete(note)
    }

}