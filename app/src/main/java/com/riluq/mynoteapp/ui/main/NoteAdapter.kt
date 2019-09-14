package com.riluq.mynoteapp.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.riluq.mynoteapp.R
import com.riluq.mynoteapp.database.Note
import com.riluq.mynoteapp.helper.NoteDiffCallback
import com.riluq.mynoteapp.ui.insert.NoteAddUpdateActivity

class NoteAdapter(val activity: Activity): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val listNotes: ArrayList<Note> = ArrayList()

    fun setListNotes(listNotes: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)

        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.tvTitle.text = listNotes[position].title
        holder.tvDescription.text = listNotes[position].description
        holder.tvDate.text = listNotes[position].date
        holder.cvNote.setOnClickListener {
            val intent = Intent(activity, NoteAddUpdateActivity::class.java)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, holder.adapterPosition)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listNotes[holder.adapterPosition])
            activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
        }
    }

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvDate: TextView = itemView.findViewById(R.id.tv_item_date)
        val cvNote: CardView = itemView.findViewById(R.id.cv_note)
    }
}