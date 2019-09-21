package com.riluq.mynoteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            NoteDatabase::class.java, "note_database")
                            .build()
                        add()
                    }
                }
            }
            return INSTANCE!!
        }

        private fun add() {
            Executors.newSingleThreadExecutor().execute {
                val list: ArrayList<Note> = ArrayList()
                for (i in 0..29) {
                    list.add(Note( null,"Tugas $i", "Belajar Modul $i", ""))
                }
                INSTANCE?.noteDao?.insertAll(list)
            }
        }
    }
}