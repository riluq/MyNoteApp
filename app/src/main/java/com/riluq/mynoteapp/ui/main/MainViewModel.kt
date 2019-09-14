package com.riluq.mynoteapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riluq.mynoteapp.database.Note
import com.riluq.mynoteapp.repository.NoteRepository

class MainViewModel(application: Application): ViewModel() {

    private var noteRepository: NoteRepository? = null

    init {
        noteRepository = NoteRepository(application)
    }


    fun getAllNote(): LiveData<List<Note>>? {
        return noteRepository?.getAllNotes()
    }
}