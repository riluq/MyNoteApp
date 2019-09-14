package com.riluq.mynoteapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.riluq.mynoteapp.database.Note

class NoteDiffCallback(val oldNoteList: List<Note>, val newNoteList: List<Note>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldNoteList.size
    }

    override fun getNewListSize(): Int {
        return newNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = oldNoteList[oldItemPosition]
        val newEmployee = newNoteList[newItemPosition]

        return oldEmployee.title == newEmployee.title
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
