package com.riluq.mynoteapp.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.riluq.mynoteapp.R
import com.riluq.mynoteapp.database.Note
import com.riluq.mynoteapp.ui.insert.NoteAddUpdateActivity

class NotePagedListAdapter(private val activity: Activity):
    PagedListAdapter<Note, NotePagedListAdapter.NoteViewHolder>(DiffCallBack) {
    private val listNotes: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        if (note != null) {
            holder.tvTitle.text = note.title
            holder.tvDescription.text = note.description
            holder.tvDate.text = note.date
            holder.cvNote.setOnClickListener {
                val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, holder.adapterPosition)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
            }
        }
    }

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        val tvDate: TextView = itemView.findViewById(R.id.tv_item_date)
        val cvNote: CardView = itemView.findViewById(R.id.cv_note)
    }

    private companion object DiffCallBack: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title.equals(newItem.title)
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
}
