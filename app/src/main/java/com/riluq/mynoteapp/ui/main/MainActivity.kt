package com.riluq.mynoteapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.riluq.mynoteapp.R
import com.riluq.mynoteapp.ViewModelFactory
import com.riluq.mynoteapp.ui.insert.NoteAddUpdateActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var adapter: NoteAdapter? = null

    companion object {
        private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MainViewModel = obtainViewModel(this)
        viewModel.getAllNote()?.observe(this, Observer { noteList ->
            if (noteList != null) {
                adapter?.setListNotes(noteList)
            }
        })

        adapter = NoteAdapter(this)

        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.setHasFixedSize(true)
        rv_notes.adapter = adapter

        fab_add.setOnClickListener {
            val intent = Intent(this, NoteAddUpdateActivity::class.java)
            startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD)
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_notes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NoteAddUpdateActivity.REQUEST_ADD) {
            if (resultCode == NoteAddUpdateActivity.RESULT_ADD) {
                showSnackbarMessage(getString(R.string.added))
            }
        } else if (requestCode == NoteAddUpdateActivity.REQUEST_UPDATE) {
            if (resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {
                showSnackbarMessage(getString(R.string.changed))
            } else if (resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                showSnackbarMessage(getString(R.string.deleted))
            }
        }
    }
}
