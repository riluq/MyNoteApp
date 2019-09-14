package com.riluq.mynoteapp.ui.insert

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.riluq.mynoteapp.R
import com.riluq.mynoteapp.ViewModelFactory
import com.riluq.mynoteapp.database.Note
import com.riluq.mynoteapp.helper.DateHelper
import kotlinx.android.synthetic.main.activity_note_add_update.*
import kotlinx.android.synthetic.main.content_note_add_update.*

class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false

    private val ALERT_DIALOG_CLOSE: Int = 10

    private val ALERT_DIALOG_DELETE: Int = 20
    private var note: Note? = null

    private var position: Int? = null
    private var noteAddUpdateViewModel: NoteAddUpdateViewModel? = null

    companion object {

        val EXTRA_NOTE = "extra_note"
        val EXTRA_POSITION = "extra_position"

        val REQUEST_ADD: Int = 100
        val RESULT_ADD: Int = 101
        val REQUEST_UPDATE: Int = 200
        val RESULT_UPDATE: Int = 201
        val RESULT_DELETE: Int = 301

        private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(NoteAddUpdateViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_update)
        setSupportActionBar(toolbar)

        noteAddUpdateViewModel = obtainViewModel(this)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String?
        val btnTitle: String?

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            if (note != null) {
                edt_title.setText(note?.title)
                edt_description.setText(note?.description)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        if (supportActionBar != null) {
            supportActionBar?.title = actionBarTitle
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        btn_submit.text = btnTitle
        btn_submit.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String?
        val dialogMessage: String?

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogTitle = getString(R.string.delete)
            dialogMessage = getString(R.string.message_delete)
        }

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                if (isDialogClose) {
                    finish()
                } else {
                    noteAddUpdateViewModel?.delete(note!!)

                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, id ->
                dialog?.cancel()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_submit) {
            val title = edt_title.text.toString().trim()
            val description = edt_description.text.toString().trim()

            if (title.isEmpty()) {
                edt_title.error = getString(R.string.empty)
            } else if (description.isEmpty()) {
                edt_description.error = getString(R.string.empty)
            } else {
                note?.title = title
                note?.description = description

                val intent = Intent()
                intent.putExtra(EXTRA_NOTE, note)
                intent.putExtra(EXTRA_POSITION, position)

                if (isEdit) {
                    noteAddUpdateViewModel?.update(note!!)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    note?.date = DateHelper.getCurrentDate()
                    note?.let { noteAddUpdateViewModel?.insert(it) }
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

}
